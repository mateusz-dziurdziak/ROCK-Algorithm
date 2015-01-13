package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.DataSetFunction;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;
import pl.dziurdziak.rock.math.Links;

/**
 * Funkcja określająca "dobroć" ewentualnego połączenia dwóch grup
 * @param <T>
 */
public class GoodnessFunction<T extends Point<? super T>> {

    /**
     * Wynik funkcji {@link pl.dziurdziak.rock.dao.DataSetFunction} dla wymaganego podobieństwa między punktami
     */
    private final double dataSetFunctionValue;

    /**
     * Konstruktor
     *
     * @param dataSetFunction
     * @param goodness
     */
    public GoodnessFunction(DataSetFunction<T> dataSetFunction, double goodness) {
        this.dataSetFunctionValue = dataSetFunction.apply(goodness);
    }

    /**
     * Oblicza wartość "dobroci" dla dwóch grup
     *
     * @param first pierwsza grupa
     * @param second druga grupa
     * @param links obiekt przechowujący liczbę wspólnych sąsiadów pomiędzy grupami
     * @return "dobroć"
     */
    public double calculate(Cluster<T> first, Cluster<T> second, Links<T> links) {
        double exp = 1 + 2 * dataSetFunctionValue;
        return links.getLinkCount(first, second)
                / (Math.pow(first.size() + second.size(), exp)
                - Math.pow(first.size(), exp)
                - Math.pow(second.size(), exp));
    }

}
