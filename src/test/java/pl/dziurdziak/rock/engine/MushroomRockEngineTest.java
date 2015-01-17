package pl.dziurdziak.rock.engine;

import org.junit.Test;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;

public class MushroomRockEngineTest {

    @Test
    public void test() {
        RockEngine<MushroomPoint> rockEngine = new MushroomRockEngine(0.8, "dataSets/mushroom/agaricus-lepiota.data");
        rockEngine.calculateClusters(20, 200);
    }

}
