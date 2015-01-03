package pl.dziurdziak.rock.dao.impl.mushroom;

import com.google.common.io.CharSource;
import com.google.common.io.Files;
import pl.dziurdziak.rock.dao.impl.AbstractMemoryPointDao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class MushroomPointMemoryDao extends AbstractMemoryPointDao<MushroomPoint> {

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

    private MushroomPoint createMushroomPoint(String line) {
        // TODO
        return new MushroomPoint(null);
    }

    @Override
    public List<MushroomPoint> getAllPoints() {
        // TODO
        return null;
    }
}
