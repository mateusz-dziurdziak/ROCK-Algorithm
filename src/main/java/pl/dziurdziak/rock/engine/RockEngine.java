package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;

public interface RockEngine<T extends Point<? super T>> {

    List<Cluster<T>> calculateClusters(int clustersCount);
}
