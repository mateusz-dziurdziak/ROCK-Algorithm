package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.math.Links;

public interface GoodnessFunction<T extends Point<? super T>> {

    double calculate(Cluster<T> first, Cluster<T> second, Links<T> links);

}
