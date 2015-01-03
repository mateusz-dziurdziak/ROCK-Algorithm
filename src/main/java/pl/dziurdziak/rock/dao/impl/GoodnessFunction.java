package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.DataSetFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.math.Links;

public interface GoodnessFunction<T extends Point> {

    double calculate(Cluster<? extends T> first, Cluster<? extends T> second, DataSetFunction<? extends T> dataSetFunction, Links links);

}
