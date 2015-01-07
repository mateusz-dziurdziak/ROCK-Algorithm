package pl.dziurdziak.rock.math;

import com.google.common.collect.*;
import org.la4j.matrix.Matrix;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;
import java.util.Set;

public class Links<T extends Point<? super T>> {

    private final Set<Cluster<T>> clusters;
    Table<Cluster<T>, Cluster<T>, Integer> linkValues = HashBasedTable.create();

    public Links(NeighbourMatrix<T> neighbourMatrix, List<Cluster<T>> clusters) {
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

    public int getLinkCount(Cluster<T> first, Cluster<T> second) {
        if (linkValues.contains(first, second)) {
            return linkValues.get(first, second);
        } else if(linkValues.contains(second, first)) {
            return linkValues.get(second, first);
        } else {
            return 0;
        }
    }

    public void add(Cluster<T> first, Cluster<T> second, int value) {
        clusters.add(first);
        clusters.add(second);
        linkValues.put(first, second, value);
    }

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
