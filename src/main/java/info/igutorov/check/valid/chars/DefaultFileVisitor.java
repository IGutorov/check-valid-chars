package info.igutorov.check.valid.chars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@FunctionalInterface
public interface DefaultFileVisitor<T extends Path> extends FileVisitor<T> {

    Logger log = LoggerFactory.getLogger(DefaultFileVisitor.class);

    void processing(T file);

    @Override
    default FileVisitResult visitFile(T file, BasicFileAttributes attrs) {
        processing(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    default FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    default FileVisitResult visitFileFailed(T file, IOException exc) {
        log.error("Not access file : {}", file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    default FileVisitResult postVisitDirectory(T dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

}
