package org.apache.catalina;

import org.apache.catalina.controller.FileController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class FileResolver {

    public static String resolve(String fileName) {
        URL url = FileController.class.getClassLoader().getResource("static" + fileName);
        Path path = Paths.get(url.getPath());

        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new NoSuchElementException("파일이 존재하지 않습니다.", e);
        }
    }
}
