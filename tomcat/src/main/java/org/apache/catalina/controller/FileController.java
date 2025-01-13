package org.apache.catalina.controller;

import org.apache.catalina.FileResolver;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpMethodName.GET;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class FileController implements Controller {

    private static final String EXTENSION_DELIMITER = "\\.";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        doGet(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String uri = request.getUri();
        String file = FileResolver.resolve(uri);
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
        return "text/" + extension + "; charset=utf-8";
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getMethod().equals(GET)
                && httpRequest.getUri().matches(".*\\.[a-zA-Z0-9]+$");
    }
}
