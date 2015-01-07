package pl.dziurdziak.rock.dao.impl.mushroom;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import pl.dziurdziak.rock.dao.impl.AbstractMemoryPointDao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DAO obsługujące gatunki grzybów
 */
public class MushroomPointMemoryDao extends AbstractMemoryPointDao<MushroomPoint> {

    public static final ImmutableList<String> ATTRIBUTES;
    static {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("cap-shape");
        builder.add("cap-surface");
        builder.add("cap-color");
        builder.add("bruises?");
        builder.add("odor");
        builder.add("gill-attachment");
        builder.add("gill-spacing");
        builder.add("gill-size");
        builder.add("gill-color");
        builder.add("stalk-shape");
        builder.add("stalk-root");
        builder.add("stalk-surface-above-ring");
        builder.add("stalk-surface-below-ring");
        builder.add("stalk-color-above-ring");
        builder.add("stalk-color-below-ring");
        builder.add("veil-type");
        builder.add("veil-color");
        builder.add("ring-number");
        builder.add("ring-type");
        builder.add("spore-print-color");
        builder.add("population");
        builder.add("habitat");
        ATTRIBUTES = builder.build();
    }

    /**
     * Obiekt rozdzielający łańcuchy tekstowe oddzielone znakiem ,
     */
    private static final Splitter ON_COMMA = Splitter.on(",");

    public MushroomPointMemoryDao(File file) {
        super(file);
    }

    @Override
    protected List<MushroomPoint> readPoints(File file) throws IOException {
        CharSource charSource = Files.asCharSource(file, Charset.defaultCharset());
        List<String> pointDescList = charSource.readLines();

        return pointDescList.stream()
                .map(this::createMushroomPoint)
                .collect(Collectors.toList());
    }

    /**
     * Tworzy punkt symbolizujący gatunek grzyba na podstawie linii w pliku z danymi
     *
     * @param line linia z danymi
     * @return punkt symbolizujący gatunek grzyba
     */
    private MushroomPoint createMushroomPoint(String line) {
        List<String> values = ON_COMMA.splitToList(line);
        // pierwsza wartość w linii zawiera skrót typu
        MushroomClass mushroomClass = MushroomClass.fromAbbrev(values.get(0));
        Map<String, String> keyToValue = new HashMap<>(ATTRIBUTES.size());
        for (int i = 0; i < ATTRIBUTES.size(); i++) {
            keyToValue.put(ATTRIBUTES.get(i), values.get(i + 1));
        }
        return new MushroomPoint(mushroomClass, keyToValue);
    }

}
