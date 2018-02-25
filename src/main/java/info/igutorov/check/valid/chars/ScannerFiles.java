package info.igutorov.check.valid.chars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ScannerFiles {

    private ScannerFiles() {
    }

    public static void scanAll(String sourceDir, Consumer<Path> action) throws IOException {
        scanAllInner(sourceDir, action::accept);
    }

    public static Collection<Path> getPaths(String sourceDir, Predicate<Path> filter) throws IOException {
        List<Path> result = new ArrayList<>();
        scanAllInner(sourceDir, p -> Optional.of(p).filter(filter).ifPresent(result::add));
        return result;
    }

    public static Collection<Path> getAllPaths(String sourceDir) throws IOException {
        return getPaths(sourceDir, p -> true);
    }

    private static void scanAllInner(String sourceDir, DefaultFileVisitor<Path> visitor) throws IOException {
        Path pathFrom = Paths.get(sourceDir);
        if (!pathFrom.toFile().exists()) {
            throw new IOException("Wrong path sourceDir : " + sourceDir);
        }
        if (!pathFrom.toFile().isDirectory()) {
            throw new IOException("sourceDir isn't directory : " + sourceDir);
        }
        Files.walkFileTree(pathFrom, visitor);
    }

}
