package pl.dziurdziak.rock.engine;

import org.junit.Test;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;

public class MushroomRockEngineTest {

    @Test
    public void test() {
        RockEngine<MushroomPoint> rockEngine = new MushroomRockEngine();
        rockEngine.calculateClusters(20);
    }
}
