package pl.dziurdziak.rock.math;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;

public class LocalHeap<T extends Point<? super T>> extends ClusterQueue<T> {

    private final Cluster<T> heapCluster;
    private final Links<T> links;
    private final GoodnessFunction<T> goodnessFunction;

    public LocalHeap(Cluster<T> cluster, Links<T> links, GoodnessFunction<T> goodnessFunction) {
        this.heapCluster = cluster;
        this.links = links;
        this.goodnessFunction = goodnessFunction;

        links.getClusters().stream().filter(otherCluster -> !otherCluster.equals(cluster)).forEach(this::add);
    }

    public Cluster<T> getHeapCluster() {
        return heapCluster;
    }

    public void add(Cluster<T> cluster) {
        if (links.getLinkCount(heapCluster, cluster) > 0) {
            double value = goodnessFunction.calculate(heapCluster, cluster, links);
            add(cluster, value);
        }
    }

    @Override
    public double getMaxValue() {
        if (size() > 0) {
            return super.getMaxValue();
        } else {
            return Double.MIN_VALUE;
        }
    }
}
