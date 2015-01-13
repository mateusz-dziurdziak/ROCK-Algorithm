package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.math.ClusterQueue;
import pl.dziurdziak.rock.math.Links;
import pl.dziurdziak.rock.math.LocalHeap;
import pl.dziurdziak.rock.math.NeighbourMatrix;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstrakcyjna klasa algorytmu ROCK
 * @param <T> typ punktu
 */
public abstract class RockEngine<T extends Point<? super T>>{

    /**
     * Oblicza zadaną liczbę grup
     *
     * @param clustersCount liczba grup
     * @param numberOfPoints liczba punktów wykorzystanych do obliczeń
     * @return obliczone grupy
     */
    public Collection<Cluster<T>> calculateClusters(int clustersCount, int numberOfPoints) {
        // pobranie obiektu dającego dostęp do przykładów z dziedziny
        PointDao<T> dao = getDao();

        // gdy liczba wszystkich przykładów jest mniejsza niż żądana liczba grup przystępujemy do zwrócenia
        // grup utworzonych na podstawie pojedynczych punktów (każdy punkt odwzorowywany jest na grupę)
        if (dao.count() <= clustersCount) {
            return dao.getAllPoints().stream().map(this::pointToCluster).collect(Collectors.toList());
        }

        // następuje pobranie losowych punktów
        List<T> points = dao.getRandomPoints(numberOfPoints);
        // losowo pobrane punkty przetwarzana są na początkowe jednoelementowe grupy
        List<Cluster<T>> clusters = points.stream().map(this::pointToCluster).collect(Collectors.toList());

        // dla utworzonych grup z wykorzystaniem funkcji określającej sąsiedztwo oraz współczynnika sąsiedztwa generowana
        // jest macierz sąsiedztwa
        NeighbourMatrix<T> neighbourMatrix = new NeighbourMatrix<>(points, getNeighbourFunction());

        // obliczana jest liczba połączeń pomiędzy grupami
        Links<T> links = new Links<>(neighbourMatrix, clusters);

        // dla każdej grupy posiadającej z jakakolwiek inną co najmniej jednego wspólnego sąsiada tworzona jest lokalna
        // kolejka grup uszergowana według funkcji dobroci
        Map<Cluster<T>, LocalHeap<T>> q =  clusters.stream().map(cluster -> new LocalHeap<>(cluster, links, getGoodnessFunction()))
                .filter(localHeap -> localHeap.size() > 0)
                .collect(Collectors.toMap(LocalHeap::getHeapCluster, (l) -> l));

        // tworzona jest globalna kolejka grup uszeregowana wg malejącej wartości będącej maksymalną wartością z lokalnej
        // kolejki dla odpowiedniej grupy
        ClusterQueue<T> globalHeap = initializeGlobalQueue(clusters, q);

        // dopóki nie zostanie osiągnięta żądana liczba grup lub nie dojdzie do sytuacji gdy nie występują już żadne grupy
        // posiadające wspólnych sasiadów wykonywane są kolejne kroki algorytmu
        while (globalHeap.size() > clustersCount
                && globalHeap.getMaxValue() != Double.MIN_VALUE) {
            // pobranie dwóch grup, których połączenie jest najbardziej optymalne (na podstawie funkcji dobroci)
            Cluster<T> u = globalHeap.getMax();
            Cluster<T> v = q.get(u).getMax();

            // połączenie grup u i v w grupę w
            Cluster<T> w = u.merge(v);
            // utworzenie pustej kolejki lokalnej dla nowo utworzonej grupy w
            q.put(w, new LocalHeap<T>(w, links, getGoodnessFunction()));

            // dla każdej grupy połączonej z u lub v wykonaj następujące operacje:
            for (Cluster<T> x: getConnectedClusters(u, v, q)) {
                // ustal nową wartość wspólnych połączeń pomiędzy x i w
                links.add(x, w, links.getLinkCount(x, u) + links.getLinkCount(x, v));

                // z listy sąsiadów x usuń powiązania do u i v
                q.get(x).delete(u);
                q.get(x).delete(v);

                // w lokalnych kolejkach dodaj nowe powiązania
                q.get(x).add(w);
                q.get(w).add(x);

                // zaktualizuj globalną kolejke (mogła zmienić się grupa, z którą najoptymalniej połączy się x)
                globalHeap.update(x, q.get(x).getMaxValue());
            }

            // usuń pozostałe odwołania do v i u
            q.remove(v);
            q.remove(u);
            globalHeap.delete(v);
            globalHeap.delete(u);

            // zaktualizuj globalna kolejkę o wartość w
            globalHeap.add(w, q.get(w).getMaxValue());
        }

        // zwróc utworzone grupy
        return globalHeap.getAll();
    }

    /**
     * @return {@link pl.dziurdziak.rock.dao.NeighbourFunction} dla zadanego typu punktów
     */
    protected abstract NeighbourFunction<T> getNeighbourFunction();

    /**
     * @return {@link pl.dziurdziak.rock.dao.PointDao} dla zadanego typu punktów
     */
    protected abstract PointDao<T> getDao();

    /**
     * Buduje grupę na podstawie punktu
     *
     * @param point punkt
     * @return grupa
     */
    protected abstract Cluster<T> pointToCluster(T point);

    /**
     * @return {@link pl.dziurdziak.rock.dao.impl.GoodnessFunction} dla zadnego typu punktów
     */
    protected abstract GoodnessFunction<T> getGoodnessFunction();

    /**
     * Tworzy globalną kolejkę zawierającą wszystkie grupy uszeregowaną wg najlepszych wartości z lokalnych
     * kolejek odpowiadających grupom
     *
     * @param clusters grupy
     * @param q mapa zawierająca lokalne kolejki dla grup
     * @return globalną kolejkę grup
     */
    private ClusterQueue<T> initializeGlobalQueue(List<Cluster<T>> clusters, Map<Cluster<T>, LocalHeap<T>> q) {
        ClusterQueue<T> clusterQueue = new ClusterQueue<>();
        for(Cluster<T> cluster : clusters) {
            if (q.containsKey(cluster)) {
                clusterQueue.add(cluster, q.get(cluster).getMaxValue());
            } else {
                clusterQueue.add(cluster, Double.MIN_VALUE);
            }
        }

        return clusterQueue;
    }

    /**
     * Zwraca grupy będące połączone z grupami u lub v
     *
     * @param u grupa pierwsza
     * @param v grupa druga
     * @param q mapa zawierająca lokalne kolejki dla grup
     * @return grupy będące połączone z grupami u lub v
     */
    private Set<Cluster<T>> getConnectedClusters(Cluster<T> u, Cluster<T> v, Map<Cluster<T>, LocalHeap<T>> q) {
        Set<Cluster<T>> connectedClusters = new HashSet<>();
        if (q.containsKey(u)) {
            connectedClusters.addAll(q.get(u).getAll());
        }
        if (q.containsKey(v)) {
            connectedClusters.addAll(q.get(v).getAll());
        }
        connectedClusters.remove(u);
        connectedClusters.remove(v);
        return connectedClusters;
    }

}
