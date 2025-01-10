package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.apache.coyote.http11.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class FileHandler implements Handler {

    private static final Map<String, String> FILE_REQUEST_URI = Map.of(
            "/register", "/register.html",
            "/login", "/login.html");
    private static final String EXTENSION_DELIMITER = "\\.";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getURI();

        URL url = FileHandler.class.getClassLoader().getResource("static" + uri);
        Path path = Paths.get(url.getPath());
        String file = Files.readString(path);

        return createHttpResponse(uri, file);
    }

    private HttpResponse createHttpResponse(String uri, String file) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(OK);

        String contentType = createFileContentType(uri);
        String fileString = String.valueOf(file.getBytes().length);
        httpResponse.setHeader(CONTENT_TYPE, contentType);
        httpResponse.setHeader(CONTENT_LENGTH, fileString);
        httpResponse.setResponseBody(file);
        return httpResponse;
    }

    private String createFileContentType(String resource) {
        String extension = resource.split(EXTENSION_DELIMITER)[1];
        return "text/" + extension + ";charset=utf-8";
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        if (FILE_REQUEST_URI.containsKey(httpRequest.getURI())) {
            httpRequest.setURI(FILE_REQUEST_URI.get(httpRequest.getURI()));
        }

        return httpRequest.getMethod().equals("GET")
                && httpRequest.getURI().matches(".*\\.[a-zA-Z0-9]+$");
    }
}
