package org.apache.catalina;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements Handler {

    private static final String DEFAULT_PATH = "static/";

    public HttpResponse handle(HttpRequest httpRequest) {
        String resource = httpRequest.getResource();

        URL url = FileHandler.class.getClassLoader().getResource(DEFAULT_PATH + resource);
        Path path = Paths.get(url.getPath());
        String fileString = "";
        try {
            fileString = Files.readString(path); // TODO : 예외처리 고민
        } catch (IOException ioException) {
            return new HttpResponse();
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setValue("HTTP/1.1 200 OK ");
        httpResponse.setValue("Content-Type: text/html;charset=utf-8 ");
        httpResponse.setValue("Content-Length: " + fileString.getBytes().length + " ");
        httpResponse.setValue("");
        httpResponse.setValue(fileString.trim());

        return httpResponse;
    }
}
