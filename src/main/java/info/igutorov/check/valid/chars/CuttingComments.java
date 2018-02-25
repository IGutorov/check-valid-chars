package info.igutorov.check.valid.chars;

import java.util.Objects;

public class CuttingComments {

    private CuttingComments() {
    }

    private static final String BEGIN_BLOCK = "/*";
    private static final String END_BLOCK = "*/";
    private static final int NOT_FOUND = -1;

    public static String clearBlockComments(String in) {
        if (Objects.isNull(in)) {
            return null;
        }
        int beginBlockPosition = in.indexOf(BEGIN_BLOCK);
        if (beginBlockPosition == NOT_FOUND) {
            return in;
        }
        StringBuilder result = new StringBuilder();
        int endBlockPosition = 0;
        while (beginBlockPosition != NOT_FOUND) {
            result.append(in.substring(endBlockPosition, beginBlockPosition));
            endBlockPosition = in.indexOf(END_BLOCK, beginBlockPosition);
            if (endBlockPosition == NOT_FOUND) {
                break;
            }
            endBlockPosition += END_BLOCK.length();
            beginBlockPosition = in.indexOf(BEGIN_BLOCK, endBlockPosition);
            if (beginBlockPosition == NOT_FOUND) {
                result.append(in.substring(endBlockPosition));
            }
        }
        return result.toString();
    }

    public static boolean isCommentedLine(String line) {
        return line.trim().startsWith("//");
    }

    public static boolean lineLikeToDoComment(String in) {
        String trimmed = in.trim();
        return trimmed.startsWith("//") && trimmed.substring(2).trim().toUpperCase().startsWith("TODO");
    }
}
