package pl.dziurdziak.rock.engine;

import com.google.common.collect.Sets;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.math.ClusterQueue;
import pl.dziurdziak.rock.math.Links;
import pl.dziurdziak.rock.math.LocalHeap;
import pl.dziurdziak.rock.math.NeighbourMatrix;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RockEngineImpl<T extends Point<? super T>> implements RockEngine<T> {

    @Override
    public List<Cluster<T>> calculateClusters(int clustersCount) {
        PointDao<T> dao = getDao();
        if (dao.count() <= clustersCount) {
            return dao.getAllPoints().stream().map(this::pointToCluster).collect(Collectors.toList());
        }

        List<T> points = dao.getRandomPoints((int) (dao.count() * 0.1));
        List<Cluster<T>> clusters = points.stream().map(this::pointToCluster).collect(Collectors.toList());

        NeighbourMatrix<T> neighbourMatrix = new NeighbourMatrix<>(points, getNeighbourFunction(), 0.8); // goodness
        Links<T> links = new Links<>(neighbourMatrix, clusters);

        Map<Cluster<T>, LocalHeap<T>> q =  clusters.stream().map(cluster -> buildLocalHeap(cluster, links, getGoodnessFunction()))
                .collect(Collectors.toMap(LocalHeap::getHeapCluster, (l) -> l));

        ClusterQueue<T> globalHeap = initializeGlobalHeap(clusters, q);

        while (globalHeap.size() > clustersCount
                && globalHeap.getMaxValue() != Double.MIN_VALUE) {
            Cluster<T> u = globalHeap.getMax();
            Cluster<T> v = q.get(u).getMax();
            globalHeap.delete(v);
            globalHeap.delete(u);

            Cluster<T> w = u.merge(v);
            q.put(w, buildLocalHeap(w, links, getGoodnessFunction()));

            Set<Cluster<T>> connectedClusters = Sets.union(q.get(u).getAll(), q.get(v).getAll());
            for (Cluster<T> x: connectedClusters) {
                links.add(x, w, links.getLinkCount(x, u) + links.getLinkCount(x, v));
                q.get(x).delete(u);
                q.get(x).delete(v);

                q.get(x).add(w);
                q.get(w).add(x);

                globalHeap.update(x, q.get(x).getMaxValue());
            }
            globalHeap.add(w, q.get(w).getMaxValue());
        }

        // TODO
        return null;
    }

    protected abstract NeighbourFunction<T> getNeighbourFunction();

    protected abstract PointDao<T> getDao();

    protected abstract Cluster<T> pointToCluster(T point);

    protected abstract GoodnessFunction<T> getGoodnessFunction();

    private LocalHeap<T> buildLocalHeap(Cluster<T> cluster, Links<T> links, GoodnessFunction<T> goodnessFunction) {
        // TODO
        return null;
    }

    public ClusterQueue<T> initializeGlobalHeap(List<Cluster<T>> clusters, Map<Cluster<T>, LocalHeap<T>> q) {
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

}
