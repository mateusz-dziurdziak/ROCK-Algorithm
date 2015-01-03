package pl.dziurdziak.rock.dao.impl.mushroom;

import pl.dziurdziak.rock.dao.Cluster;

import java.util.ArrayList;
import java.util.List;

public class MushroomCluster implements Cluster<MushroomPoint> {

    private final List<MushroomPoint> points = new ArrayList<>();

    public MushroomCluster(MushroomPoint point) {
        addPoint(point);
    }

    @Override
    public int size() {
        return points.size();
    }

    @Override
    public void addPoint(MushroomPoint point) {
        points.add(point);
    }

    @Override
    public Cluster<MushroomPoint> merge(Cluster<MushroomPoint> secondCluster) {
        // tODO
        return null;
    }
}
