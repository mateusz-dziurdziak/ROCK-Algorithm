package pl.dziurdziak.rock.dao.impl.mushroom;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import pl.dziurdziak.rock.dao.NeighbourFunction;

import static org.junit.Assert.assertFalse;

public class MushroomNeighbourFunctionTest {

    @Test
    public void testFalse() {
        MushroomPoint first = new MushroomPoint(MushroomClass.EDIBLE, ImmutableMap.of("f", "1", "s", "2", "t", "3"));
        MushroomPoint second = new MushroomPoint(MushroomClass.EDIBLE, ImmutableMap.of("f", "1", "s", "2", "t", "3"));

        NeighbourFunction<MushroomPoint> neighbourFunction = new MushroomNeighbourFunction(0.6);
        assertFalse(neighbourFunction.apply(first, second));
    }

}
