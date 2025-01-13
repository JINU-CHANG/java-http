package org.apache.catalina;

import org.apache.catalina.controller.FileController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileResolver {

    public static String resolve(String fileName) throws IOException {
        URL url = FileController.class.getClassLoader().getResource("static" + fileName);
        Path path = Paths.get(url.getPath());
        return Files.readString(path);
    }
}
