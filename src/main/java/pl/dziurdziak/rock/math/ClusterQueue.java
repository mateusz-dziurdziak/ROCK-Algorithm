package pl.dziurdziak.rock.math;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;
import java.util.Queue;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class ClusterQueue<T extends Point<? super T>> {

    private final Queue<HeapElement<T>> heapElements = new PriorityQueue<>((first, second) ->
            first.value == second.value ? 0 :
                    (first.value > second.value ? -1 : 1));

    public int size() {
        return heapElements.size();
    }

    public Cluster<T> getMax() {
        return heapElements.peek().cluster;
    }

    public double getMaxValue() {
        return heapElements.peek().value;
    }

    public void add(Cluster<T> cluster, double value) {
        HeapElement<T> heapElement = new HeapElement<>(value, cluster);
        heapElements.add(heapElement);
    }

    public boolean delete(Cluster<T> cluster) {
        int preSize = size();
        heapElements.removeIf(he -> he.cluster.equals(cluster));
        return preSize != size();
    }

    public void update(Cluster<T> x, double value) {
        delete(x);
        add(x, value);
    }

    public boolean contains(Cluster<T> x) {
        return heapElements.stream().filter(he -> he.cluster.equals(x)).collect(Collectors.toList()).size() > 0;
    }

    public Set<Cluster<T>> getAll() {
        return heapElements.stream().map(he -> he.cluster).collect(Collectors.toSet());
    }

    private static class HeapElement<T extends Point<? super T>> {
        double value;
        Cluster<T> cluster;

        HeapElement(double value, Cluster<T> cluster) {
            this.value = value;
            this.cluster = cluster;
        }
    }

}
