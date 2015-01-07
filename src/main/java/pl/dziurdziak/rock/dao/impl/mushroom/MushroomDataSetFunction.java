package pl.dziurdziak.rock.dao.impl.mushroom;

import pl.dziurdziak.rock.dao.DataSetFunction;

public class MushroomDataSetFunction implements DataSetFunction<MushroomPoint> {

    @Override
    public double apply(double goodness) {
        return 1;
    }

}
