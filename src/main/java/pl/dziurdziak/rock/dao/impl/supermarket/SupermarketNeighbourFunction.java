package pl.dziurdziak.rock.dao.impl.supermarket;

import pl.dziurdziak.rock.dao.NeighbourFunction;

/**
 * Funkcja określająca sąsiedztwo dwóch punktów w przestrzeni
 */
public class SupermarketNeighbourFunction implements NeighbourFunction<SupermarketPoint> {

    /**
     * Maksymalna odległość pomiędzy dwoma punktami, przy której są one wciąż traktowane jako sąsiedzi
     */
    private final double neighbourMaxDistance;

    public SupermarketNeighbourFunction(double neighbourMaxDistance) {
        this.neighbourMaxDistance = neighbourMaxDistance;
    }

    @Override
    public boolean apply(SupermarketPoint first, SupermarketPoint second) {
        double powLatitude = Math.pow(first.getLatitude() - second.getLatitude(), 2);
        double powLongitude = Math.pow(first.getLongitude() - second.getLongitude(), 2);
        double distance = Math.sqrt(powLatitude + powLongitude);
        return distance <= neighbourMaxDistance;
    }

}
