package pl.dziurdziak.rock.math;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import org.la4j.matrix.Matrix;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;
import java.util.Set;

public class Links<T extends Point<? super T>> {

    Table<Cluster<T>, Cluster<T>, Integer> linkValues;

    public Links(NeighbourMatrix<T> neighbourMatrix, List<Cluster<T>> clusters) {
        Matrix linkMatrix = neighbourMatrix.secondPower();

        for (int i = 0; i < neighbourMatrix.getInitialPoints().size() - 1; i++) {
            Cluster<T> first = clusters.get(i);
            for (int j = i + 1; j < neighbourMatrix.getInitialPoints().size(); j++) {
                Cluster<T> second = clusters.get(j);
                linkValues.put(first, second, (int) linkMatrix.get(i, j));
            }
        }
    }

    public int getLinkCount(Cluster<T> first, Cluster<T> second) {
        if (!linkValues.contains(first, second)) {
            return 0;
        }
        return linkValues.get(first, second);
    }

    public void add(Cluster<T> first, Cluster<T> second, int value) {
        linkValues.put(first, second, value);
    }

    public Set<Cluster<T>> getClusters() {
        return ImmutableSet.copyOf(linkValues.rowKeySet());
    }

}
