package pl.dziurdziak.rock.dao.impl.mushroom;

/**
 * Możliwe typy grzybów
 */
public enum MushroomClass {
    /**
     * Jadalny
     */
    EDIBLE("e"),
    /**
     * Trujący
     */
    POISONOUS("p");

    /**
     * Skrót typu
     */
    private String abbrev;

    /**
     * Konstruktor
     *
     * @param abbrev {@link #abbrev}
     */
    MushroomClass(String abbrev) {
        this.abbrev = abbrev;
    }

    /**
     * Pobiera typ grzyba na podstawie jego skrótu
     *
     * @param abbrev skrót typu
     * @return typ grzyba
     * @throws java.lang.IllegalArgumentException gdy skrót typu nie jest znany
     */
    public static final MushroomClass fromAbbrev(String abbrev) {
        if(EDIBLE.abbrev.equals(abbrev)) {
            return EDIBLE;
        } else if (POISONOUS.abbrev.equals(abbrev)) {
            return POISONOUS;
        }

        throw new IllegalArgumentException(abbrev);
    }
}
