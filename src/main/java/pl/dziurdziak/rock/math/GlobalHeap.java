package pl.dziurdziak.rock.math;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;

public class GlobalHeap<T extends Point<? super T>> {

    private final List<Cluster<T>> clusters;
    private final List<LocalHeap<T>> localHeaps;

    public GlobalHeap(List<Cluster<T>> clusters, List<LocalHeap<T>> localHeaps) {
        this.clusters = clusters;
        this.localHeaps = localHeaps;
    }

    public int size() {
        // TODO
        return 0;
    }

    public Cluster<T> getMax() {
        // TODO
        return null;
    }

    public void add(Cluster<T> w, double value) {
        // TODO
    }

    public void delete(Cluster<T> v) {
        // TODO
    }

    public void update(Cluster<T> x, double value) {
        // TODO
    }

}
