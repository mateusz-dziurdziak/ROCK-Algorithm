package pl.dziurdziak.rock.dao.impl.mushroom;

import pl.dziurdziak.rock.dao.NeighbourFunction;

import java.util.Objects;

/**
 * Funkcja okreslająca czy dwa typy grzybów są "sąsiadami"
 */
public class MushroomNeighbourFunction implements NeighbourFunction<MushroomPoint> {

    /**
     * Wymagane "podobieństwo" typów
     *
     * Rozumiane jako liczbę 0-1 określającą liczbę takich samych atrybutów do wszystkich atrybutów
     */
    private final double goodness;

    /**
     * Konstruktor
     *
     * @param goodness {@link #goodness}
     */
    public MushroomNeighbourFunction(double goodness) {
        this.goodness = goodness;
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

        return (double) common / MushroomPointMemoryDao.ATTRIBUTES.size() >= goodness;
    }

}
