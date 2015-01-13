package pl.dziurdziak.rock.math;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import org.la4j.matrix.Matrix;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Obiekt przechowujacy liczby powiązań pomiędzy grupami
 * @param <T> typ punktu
 */
public class Links<T extends Point<? super T>> {

    /**
     * Zbiór grup
     */
    private final Set<Cluster<T>> clusters;

    /**
     * Tabela przechowujaca wartości powiązań pomiędzy grupami. Para dwóch grup występuje w tabeli tylko raz
     */
    private Table<Cluster<T>, Cluster<T>, Integer> linkValues = HashBasedTable.create();

    /**
     * Konstruktor
     *
     * @param neighbourMatrix macierz sąsiedztwa punktów
     * @param clusters grupy
     * @throws java.lang.IllegalArgumentException gdy liczba punktów nie odpowiada liczbie grup
     */
    public Links(NeighbourMatrix<T> neighbourMatrix, List<Cluster<T>> clusters) {
        checkArgument(neighbourMatrix.getInitialPoints().size() == clusters.size());
        this.clusters = Sets.newHashSet(clusters);
        Matrix linkMatrix = neighbourMatrix.secondPower();

        for (int i = 0; i < clusters.size() - 1; i++) {
            Cluster<T> first = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                Cluster<T> second = clusters.get(j);
                setVal(first, second, (int) linkMatrix.get(i, j));
            }
        }
    }

    /**
     * Zwraca liczbę wspólnych sąsiadów dwóch grup
     *
     * @param first pierwsza grupa
     * @param second druga grupa
     * @return liczba wspólnych sąsiadów
     */
    public int getLinkCount(Cluster<T> first, Cluster<T> second) {
        if (linkValues.contains(first, second)) {
            return linkValues.get(first, second);
        } else if(linkValues.contains(second, first)) {
            return linkValues.get(second, first);
        } else {
            return 0;
        }
    }

    /**
     * Dodaje informację o liczbie wspólnych sąsiadów pomiędzy dwoma grupami. Jeśli informacja już istnieje jest aktualizowana.
     *
     * @param first pierwsza grupa
     * @param second druga grupa
     * @param value liczba wspólnych sąsiadów
     */
    public void add(Cluster<T> first, Cluster<T> second, int value) {
        if (linkValues.contains(first, second)
                || linkValues.contains(second, first)) {
            setVal(first, second, value);
        } else {
            clusters.add(first);
            clusters.add(second);
            linkValues.put(first, second, value);
        }
    }

    /**
     * @return wszystkei grupy dla których została przeprowadzona kalklulacja liczby wspólnych sąsiadów
     */
    public Set<Cluster<T>> getClusters() {
        return ImmutableSet.copyOf(clusters);
    }

    private void setVal(Cluster<T> first, Cluster<T> second, int val) {
        if (linkValues.contains(second, first)) {
            linkValues.put(second, first, val);
        } else  {
            linkValues.put(first, second, val);
        }
    }

}
