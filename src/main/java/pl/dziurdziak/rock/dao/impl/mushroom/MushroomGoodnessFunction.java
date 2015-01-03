package pl.dziurdziak.rock.dao.impl.mushroom;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.DataSetFunction;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.math.Links;

public class MushroomGoodnessFunction implements GoodnessFunction<MushroomPoint> {

    private final double dataSetFunctionValue;

    public MushroomGoodnessFunction(DataSetFunction dataSetFunction, double goodness) {
        this.dataSetFunctionValue = dataSetFunction.apply(goodness);
    }

    @Override
    public double calculate(Cluster<? extends MushroomPoint> first, Cluster<? extends MushroomPoint> second, DataSetFunction<? extends MushroomPoint> dataSetFunction,
                            Links links) {
        double exp = 1 + 2 * dataSetFunctionValue;
        return links.getLinkCount(first, second)
                / (Math.pow(first.size() + second.size(), exp)
                    - Math.pow(first.size(), exp)
                    - Math.pow(second.size(), exp));
    }

}
