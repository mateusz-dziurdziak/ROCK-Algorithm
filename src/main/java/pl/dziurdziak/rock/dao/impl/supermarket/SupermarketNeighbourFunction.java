package pl.dziurdziak.rock.dao.impl.supermarket;

import pl.dziurdziak.rock.dao.NeighbourFunction;

public class SupermarketNeighbourFunction implements NeighbourFunction<SupermarketPoint> {

    private final double neighoburMaxDistance;

    public SupermarketNeighbourFunction(double neighoburMaxDistance) {
        this.neighoburMaxDistance = neighoburMaxDistance;
    }

    @Override
    public boolean apply(SupermarketPoint first, SupermarketPoint second) {
        double powLatitude = Math.pow(first.getLatitude() - second.getLatitude(), 2);
        double powLongitude = Math.pow(first.getLongitude() - second.getLongitude(), 2);
        double distance = Math.sqrt(powLatitude + powLongitude);
        return distance <= neighoburMaxDistance;
    }

}
