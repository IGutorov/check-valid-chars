package info.igutorov.check.valid.chars;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
public class CharsCheckerTest {

    @Test
    public void checkInvalidCharsWithoutQuotesTest() {
        CharsChecker charsChecker = new CharsChecker("\"\" + ABC + \"245\\\"6\" + ZZZ + \"5555\" + 55 + bla-bla + \"STR\";");
        assertTrue(charsChecker.checkInvalidCharsWithoutQuotes(i -> i == 65));
        assertFalse(charsChecker.checkInvalidCharsWithoutQuotes(i -> i == 68));
        assertFalse(charsChecker.checkInvalidCharsWithoutQuotes(i -> i == 50));
        assertFalse(charsChecker.checkInvalidCharsWithoutQuotes(i -> i == 54));
    }

    @Test
    public void checkEmptyInvalidCharsWithoutQuotesTest() {
        CharsChecker charsCheckerNull = new CharsChecker(null);
        assertFalse(charsCheckerNull.checkInvalidCharsWithoutQuotes(i -> i == 54));
        CharsChecker charsCheckerEmpty = new CharsChecker("");
        assertFalse(charsCheckerEmpty.checkInvalidCharsWithoutQuotes(i -> i == 54));
        CharsChecker charsCheckerQuote = new CharsChecker("\"");
        assertFalse(charsCheckerQuote.checkInvalidCharsWithoutQuotes(i -> i == 54));
    }

    @Test
    public void checkNotAsciiInvalidCharsWithoutQuotesTest() {
        CharsChecker charsCheckerNull = new CharsChecker("\u0434\u0430");
        assertTrue(charsCheckerNull.checkInvalidCharsWithoutQuotes(i -> i == 0x0434));
        assertFalse(charsCheckerNull.checkInvalidCharsWithoutQuotes(i -> i == 0x0435));
        assertTrue(charsCheckerNull.checkInvalidCharsWithoutQuotes(i -> i >= 0x0080));
    }

}
