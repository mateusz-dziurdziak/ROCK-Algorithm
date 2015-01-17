package pl.dziurdziak.rock.dao.impl.supermarket;

import pl.dziurdziak.rock.dao.Point;

public class SupermarketPoint implements Point<SupermarketPoint> {

    private final double latitude;
    private final double longitude;

    public SupermarketPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
