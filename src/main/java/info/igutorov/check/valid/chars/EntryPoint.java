package info.igutorov.check.valid.chars;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

@Slf4j
public final class EntryPoint {

    private EntryPoint(String[] args) throws IOException {
        String arg0 = args.length > 0 ? args[0] : "";
        log.info("EntryPoint started Δ");
        // Δ is invalid character
        String path_Δ = Paths.get(arg0).toAbsolutePath().toString();

        Collection<Path> allFiles = ScannerFiles.getPaths(path_Δ,
                p -> p.toFile().toString().endsWith(".java"));

        allFiles.stream()
                .map(p -> new FileData(p, true))
                .filter(d -> Objects.nonNull(d.getData()))
                .forEach(this::checkData);
    }

    private void checkData(FileData fileData) {
        String[] arrayLine = fileData.getData().split("\n");
        for (int i = 0; i < arrayLine.length; i++) {
            if (new CharsChecker(arrayLine[i]).checkInvalidCharsWithoutQuotes(d -> d >= 0x0080)) {
                log.info("file = {}", fileData.getFile().toAbsolutePath().toString());
                log.info("line # {} : {}", i, arrayLine[i]);
            }
        }

    }

    public static void main(String[] args) {
        try {
            new EntryPoint(args);
        } catch (IOException e) {
            log.error("IOException ", e);
        }
    }

}
