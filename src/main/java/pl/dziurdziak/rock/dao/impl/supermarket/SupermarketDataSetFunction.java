package pl.dziurdziak.rock.dao.impl.supermarket;

import pl.dziurdziak.rock.dao.DataSetFunction;

public class SupermarketDataSetFunction implements DataSetFunction<SupermarketPoint> {

    @Override
    public double apply(double goodness) {
        return 1;
    }

}
