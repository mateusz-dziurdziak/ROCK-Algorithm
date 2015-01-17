package pl.dziurdziak.rock.dao.impl.supermarket;

import pl.dziurdziak.rock.dao.Point;

/**
 * Punkt przechowujący informację na temat położenia supermarketu
 */
public class SupermarketPoint implements Point<SupermarketPoint> {

    /**
     * Długość geograficzna
     */
    private final double longitude;

    /**
     * Szerokość geograficzna
     */
    private final double latitude;

    /**
     * Konstruktor
     *
     * @param longitude {@link #longitude}
     * @param latitude {@link #latitude}
     */
    public SupermarketPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * @return {@link #latitude}
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return {@link #longitude}
     */
    public double getLongitude() {
        return longitude;
    }

}
