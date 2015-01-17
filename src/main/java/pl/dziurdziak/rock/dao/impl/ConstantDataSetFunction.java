package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.DataSetFunction;
import pl.dziurdziak.rock.dao.Point;

/**
 * Funkcja zbioru punktów zwracająca stała wartość
 * @param <T> typ punktu
 */
public class ConstantDataSetFunction<T extends Point<? super T>> implements DataSetFunction<T> {

    /**
     * Wartość zwracana przez funkcję
     */
    private final double constantValue;

    /**
     * Konstruktor
     *
     * @param constantValue {@link #constantValue}
     */
    public ConstantDataSetFunction(double constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public double apply(double goodness) {
        return constantValue;
    }
}
