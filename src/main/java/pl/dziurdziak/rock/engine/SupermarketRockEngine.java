package pl.dziurdziak.rock.engine;

import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.ClusterImpl;
import pl.dziurdziak.rock.dao.impl.ConstantDataSetFunction;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.rock.dao.impl.supermarket.SupermarketNeighbourFunction;
import pl.dziurdziak.rock.dao.impl.supermarket.SupermarketPoint;
import pl.dziurdziak.rock.dao.impl.supermarket.SupermarketPointMemoryDao;

import java.io.File;

public class SupermarketRockEngine extends RockEngine<SupermarketPoint> {

    private final double goodness;
    private final double maxNeighbourDistance;
    private final String filePath;

    public SupermarketRockEngine(double goodness, String filePath, double maxNeighbourDistance) {
        this.goodness = goodness;
        this.filePath = filePath;
        this.maxNeighbourDistance = maxNeighbourDistance;
    }

    @Override
    protected NeighbourFunction<SupermarketPoint> getNeighbourFunction() {
        return new SupermarketNeighbourFunction(maxNeighbourDistance);
    }

    @Override
    protected PointDao<SupermarketPoint> getDao() {
        return new SupermarketPointMemoryDao(new File(filePath));
    }

    @Override
    protected Cluster<SupermarketPoint> pointToCluster(SupermarketPoint point) {
        return new ClusterImpl(point);
    }

    @Override
    protected GoodnessFunction<SupermarketPoint> getGoodnessFunction() {
        return new GoodnessFunction<>(new ConstantDataSetFunction<>(1), goodness);
    }

}
