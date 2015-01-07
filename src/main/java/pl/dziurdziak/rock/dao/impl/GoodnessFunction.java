package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.DataSetFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;
import pl.dziurdziak.rock.math.Links;

public class GoodnessFunction<T extends Point<? super T>> {

    private final double dataSetFunctionValue;

    public GoodnessFunction(DataSetFunction<T> dataSetFunction, double goodness) {
        this.dataSetFunctionValue = dataSetFunction.apply(goodness);
    }

    public double calculate(Cluster<T> first, Cluster<T> second, Links<T> links) {
        double exp = 1 + 2 * dataSetFunctionValue;
        return links.getLinkCount(first, second)
                / (Math.pow(first.size() + second.size(), exp)
                - Math.pow(first.size(), exp)
                - Math.pow(second.size(), exp));
    }

}
