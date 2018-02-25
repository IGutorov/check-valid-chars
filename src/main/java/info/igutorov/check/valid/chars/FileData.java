package info.igutorov.check.valid.chars;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@ToString
@Getter
@Slf4j
public class FileData {

    private final Path file;
    private final String data;

    public FileData(Path file, boolean removeComments) {
        this.file = file;
        this.data = getData(file)
                .map(d -> removeComments ? removeAllComments(d) : d)
                .orElse(null);
    }

    public FileData(Path file) {
        this(file, false);
    }

    private String removeAllComments(String in) {
        String result = CuttingComments.clearBlockComments(in);
        StringBuilder sb = new StringBuilder();
        boolean hasLineComment = false;
        for (String line : result.split("\n")) {
            if (CuttingComments.isCommentedLine(line)) {
                hasLineComment = true;
            } else {
                sb.append(line).append("\n");
            }
        }
        return hasLineComment ? sb.toString() : result;
    }

    private static Optional<String> getData(Path path) {
        if (Files.notExists(path) || Files.isDirectory(path)) {
            log.error("File not exists");
            return Optional.empty();
        }
        try {
            return Optional.of(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error reading file {}", e);
            return Optional.empty();
        }
    }

}
