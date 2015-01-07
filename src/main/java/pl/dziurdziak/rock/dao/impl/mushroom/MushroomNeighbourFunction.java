package pl.dziurdziak.rock.dao.impl.mushroom;

import pl.dziurdziak.rock.dao.NeighbourFunction;

import java.util.Objects;

public class MushroomNeighbourFunction implements NeighbourFunction<MushroomPoint> {

    private final double goodnes;

    public MushroomNeighbourFunction(double goodnes) {
        this.goodnes = goodnes;
    }

    @Override
    public boolean apply(MushroomPoint first, MushroomPoint second) {
        int common = 0;
        for (String attribute : MushroomPointMemoryDao.ATTRIBUTES) {
            if (first.getValue(attribute) != null
                    && first.getValue(attribute).equals(second.getValue(attribute))) {
                common++;
            }
        }

        return (double) common / MushroomPointMemoryDao.ATTRIBUTES.size() >= goodnes;
    }

}
