package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;

public abstract class CategoricalNeighbourFunctionImpl<T extends Point<? super T>> implements NeighbourFunction<T> {

    @Override
    public boolean apply(T first, T second, double goodness) {
        return false;
    }

}
