package pl.dziurdziak.rock.dao.impl;

import com.google.common.collect.Lists;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;

import java.util.ArrayList;
import java.util.List;

public class ClusterImpl<T extends Point<? super T>> implements Cluster<T> {

    /**
     * Lista punktów w danej grupie
     */
    private final List<T> points = new ArrayList<>();

    /**
     * Konstruktor
     *
     * @param point punkt początkowy
     */
    public ClusterImpl(T point) {
        this.points.add(point);
    }

    /**
     * Konstruktor tworzący grupę na podstawie dwóch list punktów, które zostaną połączone
     *
     * @param points pierwsza lista punktów
     * @param secondPoints druga lista punktów
     */
    public ClusterImpl(List<T> points, List<T> secondPoints) {
        this.points.addAll(points);
        this.points.addAll(secondPoints);
    }

    @Override
    public int size() {
        return points.size();
    }

    @Override
    public List<T> getPoints() {
        return Lists.newArrayList(points);
    }

    @Override
    public Cluster<T> merge(Cluster<T> secondCluster) {
        return new ClusterImpl(getPoints(), secondCluster.getPoints());
    }

}
