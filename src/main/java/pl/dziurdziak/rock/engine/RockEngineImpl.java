package pl.dziurdziak.rock.engine;

import com.google.common.collect.Sets;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.math.GlobalHeap;
import pl.dziurdziak.rock.math.Links;
import pl.dziurdziak.rock.math.LocalHeap;
import pl.dziurdziak.rock.math.LocalHeapQueue;
import pl.dziurdziak.rock.math.NeighbourMatrix;

import java.util.List;
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

        NeighbourMatrix neighbourMatrix = new NeighbourMatrix(points, getNeighbourFunction());
        Links links = new Links(neighbourMatrix, clusters);

        List<LocalHeap<T>> localHeaps =  clusters.stream().map(cluster -> buildLocalHeap(cluster, links, getGoodnessFunction()))
                .collect(Collectors.toList());
        LocalHeapQueue q = new LocalHeapQueue(localHeaps);

        GlobalHeap<T> globalHeap = new GlobalHeap<>(clusters, localHeaps);

        while (globalHeap.size() > clustersCount) {
            Cluster<T> u = globalHeap.getMax();
            Cluster<T> v = q.getByCluster(u).getMax();
            globalHeap.delete(v);
            globalHeap.delete(u);

            Cluster<T> w = u.merge(v);
            localHeaps.add(buildLocalHeap(w, links, getGoodnessFunction()));

            Set<Cluster<T>> connectedClusters = Sets.union(q.getByCluster(u).getAll(), q.getByCluster(v).getAll());
            for (Cluster<T> x: connectedClusters) {
                links.add(x, w, links.getLinkCount(x, u) + links.getLinkCount(x, v));
                q.getByCluster(x).delete(u);
                q.getByCluster(x).delete(v);

                q.getByCluster(x).add(w);
                q.getByCluster(w).add(x);

                globalHeap.update(x, q.getByCluster(x).getMaxValue());
            }
            globalHeap.add(w, q.getByCluster(w).getMaxValue());
        }

        // TODO
        return null;
    }

    protected abstract NeighbourFunction<T> getNeighbourFunction();

    protected abstract PointDao<T> getDao();

    protected abstract Cluster<T> pointToCluster(T point);

    protected abstract GoodnessFunction<T> getGoodnessFunction();

    private LocalHeap<T> buildLocalHeap(Cluster<T> cluster, Links links, GoodnessFunction<T> goodnessFunction) {
        // TODO
        return null;
    }

}
