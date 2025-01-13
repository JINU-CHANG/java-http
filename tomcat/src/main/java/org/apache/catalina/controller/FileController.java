package org.apache.catalina.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpMethodName.GET;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class FileController implements Controller {

    private static final Map<String, String> FILE_REQUEST_URI = Map.of(
            "/register", "/register.html",
            "/login", "/login.html");
    private static final String EXTENSION_DELIMITER = "\\.";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        doGet(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String uri = request.getUri();

        URL url = FileController.class.getClassLoader().getResource("static" + uri);
        Path path = Paths.get(url.getPath());
        String file = Files.readString(path);

        createHttpResponse(response, uri, file);
    }

    private void createHttpResponse(HttpResponse response, String uri, String file) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(OK);

        String contentType = createFileContentType(uri);
        String fileString = String.valueOf(file.getBytes().length);
        response.setHeader(CONTENT_TYPE, contentType);
        response.setHeader(CONTENT_LENGTH, fileString);
        response.setResponseBody(file);
    }

    private String createFileContentType(String resource) {
        String extension = resource.split(EXTENSION_DELIMITER)[1];
        return "text/" + extension + ";charset=utf-8";
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        if (FILE_REQUEST_URI.containsKey(httpRequest.getUri())) {
            httpRequest.setUri(FILE_REQUEST_URI.get(httpRequest.getUri()));
        }

        return httpRequest.getMethod().equals(GET)
                && httpRequest.getUri().matches(".*\\.[a-zA-Z0-9]+$");
    }
}
