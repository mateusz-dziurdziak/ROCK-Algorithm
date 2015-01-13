package pl.dziurdziak.rock.math;

import com.google.common.base.Preconditions;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Lokalna kolejka przechowująca grupy wg ich dobroci względem innej
 * @param <T> typ punktu
 */
public class LocalHeap<T extends Point<? super T>> extends ClusterQueue<T> {

    /**
     * Grupa, z którą powiązana jest kolejka
     */
    private final Cluster<T> heapCluster;

    /**
     * Obiekt przechowujący informację o powiązaniach między grupami
     */
    private final Links<T> links;

    /**
     * Funkcja oceniająca "dobroć" połączenia dwóch grup
     */
    private final GoodnessFunction<T> goodnessFunction;

    /**
     * Konstruktor
     *
     * @param heapCluster {@link #heapCluster}
     * @param links {@link #links}
     * @param goodnessFunction {@link #goodnessFunction}
     */
    public LocalHeap(Cluster<T> heapCluster, Links<T> links, GoodnessFunction<T> goodnessFunction) {
        this.heapCluster = heapCluster;
        this.links = links;
        this.goodnessFunction = goodnessFunction;

        links.getClusters().stream().filter(otherCluster -> !otherCluster.equals(heapCluster)).forEach(this::add);
    }

    /**
     * @return grupę, z którą powiązana jest kolejka
     */
    public Cluster<T> getHeapCluster() {
        return heapCluster;
    }

    /**
     * Dodaje grupę do kolejki
     *
     * @param cluster grupa
     * @throws java.lang.IllegalArgumentException gdy przekazana grupa jest równa grupie, do której należy lokalna kolejka
     */
    public void add(Cluster<T> cluster) {
        checkArgument(!heapCluster.equals(cluster));

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
