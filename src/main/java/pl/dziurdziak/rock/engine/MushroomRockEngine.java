package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.dao.impl.mushroom.*;

import java.io.File;

public class MushroomRockEngine extends AbstractRockEngine<MushroomPoint> {

    @Override
    protected NeighbourFunction<MushroomPoint> getNeighbourFunction() {
        return new MushroomNeighbourFunction(0.8);
    }

    @Override
    protected PointDao<MushroomPoint> getDao() {
        return new MushroomPointMemoryDao(new File("dataSets/mushroom/agaricus-lepiota.data"));
    }

    @Override
    protected Cluster<MushroomPoint> pointToCluster(MushroomPoint point) {
        return new MushroomCluster(point);
    }

    @Override
    protected GoodnessFunction<MushroomPoint> getGoodnessFunction() {
        return new GoodnessFunction<>(new MushroomDataSetFunction(), 0.8);
    }

}
