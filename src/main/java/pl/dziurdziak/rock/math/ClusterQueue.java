package pl.dziurdziak.rock.math;

import com.google.common.base.Preconditions;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Kolejka grup
 * @param <T> typ punktu
 */
public class ClusterQueue<T extends Point<? super T>> {

    /**
     * Wewnętrza kolejka elementów zawierających grupę oraz wartość z nią powiązaną. Uszeregowana malejąco wg wartości
     */
    private final Queue<HeapElement<T>> heapElements = new PriorityQueue<>((first, second) ->
            first.value == second.value ? 0 :
                    (first.value > second.value ? -1 : 1));

    /**
     * @return rozmiar kolejki
     */
    public int size() {
        return heapElements.size();
    }

    /**
     * @return grupę będącą na początku kolejki
     * @throws java.lang.IllegalArgumentException gdy kolejka jest pusta
     */
    public Cluster<T> getMax() {
        checkArgument(size() > 0);
        return heapElements.peek().cluster;
    }

    /**
     * @return wartośc powiązaną z grupą będącą na początku kolejki
     * @throws java.lang.IllegalArgumentException gdy kolejka jest pusta
     */
    public double getMaxValue() {
        checkArgument(size() > 0);
        return heapElements.peek().value;
    }

    /**
     * Dodaje grupę wraz z zadaną wartością
     *
     * @param cluster grupa
     * @param value wartość
     */
    public void add(Cluster<T> cluster, double value) {
        HeapElement<T> heapElement = new HeapElement<>(value, cluster);
        heapElements.add(heapElement);
    }

    /**
     * Usuwa grupę z kolejki
     *
     * @param cluster grupa
     * @return {@code true} jeśli grupa istniała w kolejce, {@code false} wpp
     */
    public boolean delete(Cluster<T> cluster) {
        int preSize = size();
        heapElements.removeIf(he -> he.cluster.equals(cluster));
        return preSize != size();
    }

    /**
     * Zmienia wartość grup. Jeśli grupy nie ma w kolejce to ją dodaje
     *
     * @param cluster grupa
     * @param value wartość
     */
    public void update(Cluster<T> cluster, double value) {
        delete(cluster);
        add(cluster, value);
    }

    /**
     * Zwraca informację czy kolejka zawiera grupę
     *
     * @param cluster grupa
     * @return {@code true} jeśli kolejka zawiera grupę, {@code false} wpp
     */
    public boolean contains(Cluster<T> cluster) {
        return heapElements.stream().filter(he -> he.cluster.equals(cluster)).collect(Collectors.toList()).size() > 0;
    }

    /**
     * @return wszystkie grupy z kolejki
     */
    public Set<Cluster<T>> getAll() {
        return heapElements.stream().map(he -> he.cluster).collect(Collectors.toSet());
    }

    /**
     * Klasa wykoszystywana w wewnętrznej kolejce
     * @param <T> typ punktu
     */
    private static class HeapElement<T extends Point<? super T>> {
        /**
         * Wartość elementu
         */
        double value;

        /**
         * Grupa
         */
        Cluster<T> cluster;

        HeapElement(double value, Cluster<T> cluster) {
            this.value = value;
            this.cluster = cluster;
        }
    }

}
