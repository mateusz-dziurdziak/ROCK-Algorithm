package pl.dziurdziak.rock.engine;

import org.junit.Test;
import pl.dziurdziak.App;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.impl.supermarket.SupermarketPoint;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class SupermarketRockEngineTest {


    @Test
    public void test() {
        RockEngine<SupermarketPoint> rockEngine = new SupermarketRockEngine(0.8, "dataSets/nzSupermarkets/nz-supermarkets.csv", 10);
        rockEngine.calculateClusters(20, 200);
    }

    @Test
    public void createChartTest() throws IOException {
        RockEngine<SupermarketPoint> rockEngine = new SupermarketRockEngine(0.8, "dataSets/nzSupermarkets/nz-supermarkets.csv", 4);
        Collection<Cluster<SupermarketPoint>> clusters = rockEngine.calculateClusters(20, 600);
        App.createChart("aaa.png", clusters);
        new File("aaa.png").delete();
    }

}
