package pl.dziurdziak.rock.dao.impl.supermarket;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import pl.dziurdziak.rock.dao.impl.AbstractMemoryPointDao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO obsługujące położenie supermarketów
 */
public class SupermarketPointMemoryDao extends AbstractMemoryPointDao<SupermarketPoint> {

    /**
     * Wyrażenie regularne odpowiadające informacji na temat położenia pkt
     */
    @VisibleForTesting
    static final String POINT_REGEX = "\"POINT \\(([0-9]*\\.[0-9]*) (-[0-9]*\\.[0-9]*)\\)\"";

    /**
     * Obiekt rozdzielający łańcuch znakowy na podstawie znaku ','
     */
    private static final Splitter ON_COMMA_SPLITTER = Splitter.on(',');

    /**
     * Skompilowane wyrazenie {@link #POINT_REGEX}
     */
    private static final Pattern PATTERN = Pattern.compile(POINT_REGEX);

    /**
     * Konstruktor tworzący DAO poprzez wczytanie danych z pliku.
     *
     * @param file plik z danymi
     */
    public SupermarketPointMemoryDao(File file) {
        super(file);
    }

    @Override
    protected List<SupermarketPoint> readPoints(File file) throws IOException {
        CharSource charSource = Files.asCharSource(file, Charset.defaultCharset());
        List<String> lines = charSource.readLines();

        boolean isHeader = true;
        List<SupermarketPoint> supermarketPoints = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (isHeader) {
                isHeader = false;
                continue;
            }

            supermarketPoints.add(createSupermarketPoint(line));
        }

        return supermarketPoints;
    }

    private SupermarketPoint createSupermarketPoint(String description) {
        String coordinates = ON_COMMA_SPLITTER.splitToList(description).get(0);
        Matcher matcher = PATTERN.matcher(coordinates);
        matcher.matches();
        double longitude = Double.parseDouble(matcher.group(1));
        double latitude = Double.parseDouble(matcher.group(2));
        return new SupermarketPoint(longitude, latitude);
    }

}
