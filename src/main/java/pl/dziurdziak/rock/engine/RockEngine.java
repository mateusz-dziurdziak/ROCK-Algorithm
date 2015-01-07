package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.Collection;

public interface RockEngine<T extends Point<? super T>> {

    Collection<Cluster<T>> calculateClusters(int clustersCount);

}
