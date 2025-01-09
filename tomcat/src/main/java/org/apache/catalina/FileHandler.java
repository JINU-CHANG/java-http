package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.coyote.http11.StatusLine.HTTP_VERSION;
import static org.apache.coyote.http11.StatusLine.STATUS_CODE;
import static org.apache.coyote.http11.StatusLine.STATUS_MESSAGE;

public class FileHandler implements Handler {

    private static final String DEFAULT_PATH = "static/";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = httpRequest.getURI();

        URL url = FileHandler.class.getClassLoader().getResource(DEFAULT_PATH + uri);
        Path path = Paths.get(url.getPath());
        String fileString = "";
        try {
            fileString = Files.readString(path); // TODO : 예외처리 고민
        } catch (IOException ioException) {
            return new HttpResponse();
        }

        HttpResponse httpResponse = new HttpResponse();
        setStatusLine(httpResponse);
        setHeader(httpResponse, uri, fileString);
        setResponseBody(httpResponse, fileString);
        return httpResponse;
    }

    private void setStatusLine(HttpResponse httpResponse) {
        httpResponse.setStatusLine(HTTP_VERSION, "HTTP/1.1");
        httpResponse.setStatusLine(STATUS_CODE, "200");
        httpResponse.setStatusLine(STATUS_MESSAGE, "OK");
    }

    private void setHeader(HttpResponse httpResponse, String uri, String fileString) {
        httpResponse.setHeader("Content-Type", convertToFileContentType(uri));
        httpResponse.setHeader("Content-Length", String.valueOf(fileString.getBytes().length));
    }

    private String convertToFileContentType(String resource) {
        return "text/" + resource.split("\\.")[1] + ";charset=utf-8";
    }

    private void setResponseBody(HttpResponse httpResponse, String value) {
        httpResponse.setResponseBody(value);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getMethod().equals("GET") && httpRequest.getURI().matches(".*\\.[a-zA-Z0-9]+$");
    }
}
