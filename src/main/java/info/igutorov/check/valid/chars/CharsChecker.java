package info.igutorov.check.valid.chars;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;

@Slf4j
public class CharsChecker {

    private static final int ESCAPE_CHARACTER_CODE = "\\".charAt(0);
    private static final int QUOTE_CHARACTER_CODE = "\"".charAt(0);
    private static final int NOT_DEFINED = -1;
    private static final int NOT_FOUND = -1;

    private final String line;

    private List<Interval> quotedIntervalList;

    public CharsChecker(String line) {
        this.line = line;
    }

    /**
     * Проверят наличие в строке недопустимых символов не в кавычках
     *
     * @param invalidCharacters признак недопустимого кода для символа
     * @return возвращает true если проверямая строка содержит недопустимый символ не в кавычках
     */
    public boolean checkInvalidCharsWithoutQuotes(IntPredicate invalidCharacters) {
        if (Objects.isNull(line)) {
            return false;
        }
        for (int i = 0; i < line.length(); i++) {
            if (invalidCharacters.test(line.charAt(i))) {
                int fi = i;
                if (getQuotedIntervalList().stream().noneMatch(q -> q.contain(fi))) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Interval> getQuotedIntervalList() {
        if (Objects.isNull(quotedIntervalList)) {
            quotedIntervalList = initQuotedIntervalList();
        }
        return quotedIntervalList;
    }

    private List<Interval> initQuotedIntervalList() {
        List<Interval> result = new ArrayList<>();
        int openQuotePosition = NOT_DEFINED;
        int quotePosition = line.indexOf(QUOTE_CHARACTER_CODE);
        while (quotePosition != NOT_FOUND) {
            if (openQuotePosition == NOT_DEFINED) {
                openQuotePosition = quotePosition;
            } else if (line.charAt(quotePosition - 1) != ESCAPE_CHARACTER_CODE) {
                result.add(new Interval(openQuotePosition, quotePosition));
                openQuotePosition = NOT_DEFINED;
            }
            quotePosition = line.indexOf(QUOTE_CHARACTER_CODE, quotePosition + 1);
        }
        return result;
    }

    @ToString
    @AllArgsConstructor
    private static class Interval {
        private final int from;
        private final int to;

        private boolean contain(int in) {
            return from < in && in < to;
        }
    }

}
