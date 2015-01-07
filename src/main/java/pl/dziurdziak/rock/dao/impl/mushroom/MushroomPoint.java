package pl.dziurdziak.rock.dao.impl.mushroom;

import com.google.common.collect.ImmutableMap;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.impl.CategoricalPoint;

import java.util.Map;
import java.util.Set;

/**
 * Punkt symbolizujący gatunek grzyba
 */
public class MushroomPoint implements Point<MushroomPoint> {

    /**
     * Typ grzyba
     */
    private final MushroomClass clazz;

    /**
     * Atrybuty gatnku
     */
    private final Map<String, String> attributesToValuesMap;

    /**
     * Konstruktor
     *
     * @param clazz {@link #clazz}
     * @param attributesToValuesMap {@link #attributesToValuesMap}
     */
    public MushroomPoint(MushroomClass clazz, Map<String, String> attributesToValuesMap) {
        this.attributesToValuesMap = ImmutableMap.copyOf(attributesToValuesMap);
        this.clazz = clazz;
    }

    // TODO
    public Set<String> getAttributes() {
        return attributesToValuesMap.keySet();
    }

    /**
     * Zwraca wartość atrybutu o zadanej nazwie
     *
     * @param attribute nazwa atrybutu
     * @return wartość atrybutu
     */
    public String getValue(String attribute) {
        return attributesToValuesMap.get(attribute);
    }

    /**
     * @return {@link #clazz}
     */
    public MushroomClass getClazz() {
        return clazz;
    }

}
