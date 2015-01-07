package pl.dziurdziak.rock.dao.impl.mushroom;

import com.google.common.collect.Lists;
import pl.dziurdziak.rock.dao.Cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Grupa połączonych punktów symbolizujących grzyby
 */
public class MushroomCluster implements Cluster<MushroomPoint> {

    /**
     * Lista punktów w danej grupie
     */
    private final List<MushroomPoint> points = new ArrayList<>();

    /**
     * Konstruktor
     *
     * @param point punkt początkowy
     */
    public MushroomCluster(MushroomPoint point) {
        this.points.add(point);
    }

    /**
     * Konstruktor tworzący grupę na podstawie dwóch list punktów, które zostaną połączone
     *
     * @param points pierwsza lista punktów
     * @param secondPoints druga lista punktów
     */
    public MushroomCluster(List<MushroomPoint> points, List<MushroomPoint> secondPoints) {
        this.points.addAll(points);
        this.points.addAll(secondPoints);
    }

    @Override
    public int size() {
        return points.size();
    }

    @Override
    public List<MushroomPoint> getPoints() {
        return Lists.newArrayList(points);
    }

    @Override
    public Cluster<MushroomPoint> merge(Cluster<MushroomPoint> secondCluster) {
        return new MushroomCluster(getPoints(), secondCluster.getPoints());
    }

}
