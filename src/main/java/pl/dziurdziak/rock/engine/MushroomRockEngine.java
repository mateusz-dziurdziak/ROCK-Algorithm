package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.ClusterImpl;
import pl.dziurdziak.rock.dao.impl.ConstantDataSetFunction;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomNeighbourFunction;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPointMemoryDao;

import java.io.File;

public class MushroomRockEngine extends RockEngine<MushroomPoint> {

    private final double goodness;
    private final String filePath;

    public MushroomRockEngine(double goodness, String filePath) {
        this.goodness = goodness;
        this.filePath = filePath;
    }

    @Override
    protected NeighbourFunction<MushroomPoint> getNeighbourFunction() {
        return new MushroomNeighbourFunction(goodness);
    }

    @Override
    protected PointDao<MushroomPoint> getDao() {
        return new MushroomPointMemoryDao(new File(filePath));
    }

    @Override
    protected Cluster<MushroomPoint> pointToCluster(MushroomPoint point) {
        return new ClusterImpl(point);
    }

    @Override
    protected GoodnessFunction<MushroomPoint> getGoodnessFunction() {
        return new GoodnessFunction<>(new ConstantDataSetFunction<MushroomPoint>(1), goodness);
    }

}
