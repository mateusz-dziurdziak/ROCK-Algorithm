package pl.dziurdziak.rock.dao.impl.supermarket;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SupermarketPointMemoryDaoTest {

    @Test
    public void testPattern() {
        Pattern pattern = Pattern.compile(SupermarketPointMemoryDao.POINT_REGEX);

        assertTrue(pattern.matcher("\"POINT (174.11 -99.1232131)\"").matches());
        assertTrue(pattern.matcher("\"POINT (174.450531000001035 -36.67633599999948)\"").matches());
    }

    @Test
    public void testPatternExtract() {
        Pattern pattern = Pattern.compile(SupermarketPointMemoryDao.POINT_REGEX);

        assertEquals("174.11", getGroup(pattern, "\"POINT (174.11 -99.1232131)\"", 1));
        assertEquals("-99.1232131", getGroup(pattern, "\"POINT (174.11 -99.1232131)\"", 2));
    }

    private String getGroup(Pattern pattern, String text, int num) {
        Matcher matcher = pattern.matcher(text);
        matcher.matches();
        return matcher.group(num);
    }

}
