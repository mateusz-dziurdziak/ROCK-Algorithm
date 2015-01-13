package pl.dziurdziak.rock.dao.impl.mushroom;

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MushroomPointMemoryDaoTest {

    @Test
    public void testImport() {
        MushroomPointMemoryDao dao = new MushroomPointMemoryDao(new File("dataSets/mushroom/agaricus-lepiota.data"));
    }

    @Test
    public void testGetAll() {
        MushroomPointMemoryDao dao = new MushroomPointMemoryDao(new File("dataSets/mushroom/agaricus-lepiota.data"));
        assertThat(dao.getAllPoints()).hasSize(8124);
    }

    @Test
    public void testGetRandom() {
        MushroomPointMemoryDao dao = new MushroomPointMemoryDao(new File("dataSets/mushroom/agaricus-lepiota.data"));
        assertThat(dao.getRandomPoints(100)).hasSize(100);
        assertThat(dao.getRandomPoints(7000)).hasSize(7000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomException() {
        MushroomPointMemoryDao dao = new MushroomPointMemoryDao(new File("dataSets/mushroom/agaricus-lepiota.data"));
        dao.getRandomPoints(10000);
    }

}
