package pl.dziurdziak.rock.dao.impl;

import com.google.common.collect.ImmutableMap;
import pl.dziurdziak.rock.dao.Point;

import java.util.Map;

public class CategoricalPoint implements Point<CategoricalPoint> {

    private final Map<String, String> attributesToValuesMap;

    public CategoricalPoint(Map<String, String> attributesToValuesMap) {
        this.attributesToValuesMap = ImmutableMap.copyOf(attributesToValuesMap);
    }

}
