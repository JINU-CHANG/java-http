package org.apache.coyote.http11.response;

import org.apache.coyote.http11.common.ContentType;
import org.apache.coyote.http11.common.HttpHeaderName;
import org.apache.coyote.http11.common.HttpVersion;
import java.util.Map.Entry;

import static org.apache.coyote.http11.common.ContentTypeName.TEXT_HTML;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpHeaderName.SET_COOKIE;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.FOUND;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class HttpResponse {

    private static final String LINE_DELIMITER = "\r\n";
    private static final String DELIMITER = " ";

    private final StatusLine statusLine;
    private final ResponseHeader requestHeader;
    private String responseBody;

    public HttpResponse () {
        this.statusLine = new StatusLine();
        this.requestHeader = new ResponseHeader();
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        statusLine.setHttpVersion(httpVersion);
    }

    public void setStatusCode(StatusCode statusCode) {
        statusLine.setStatusCode(statusCode);
    }

    public void setHeader(HttpHeaderName key, String value) {
        requestHeader.setHeader(key, value);
    }

    public void setResponseBody(String value) {
        responseBody = value;
    }

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getHeader(HttpHeaderName header) {
        return requestHeader.getHeader(header);
    }

    public String getHttpResponse() {
        StringBuilder responseBuilder = new StringBuilder();
        joinStatusLine(responseBuilder);
        joinHeader(responseBuilder);
        joinResponseBody(responseBuilder);
        return responseBuilder.toString();
    }

    private void joinStatusLine(StringBuilder responseBuilder) {
        responseBuilder.append(statusLine.getHttpVersion().value).append(DELIMITER)
                .append(statusLine.getStatusCode()).append(DELIMITER)
                .append(statusLine.getStatusMessage()).append(DELIMITER)
                .append(LINE_DELIMITER);
    }

    private void joinHeader(StringBuilder responseBuilder) {
        for (Entry<HttpHeaderName, String> keyValue : requestHeader.getHeaders().entrySet()) {
            responseBuilder.append(keyValue.getKey().name).append(": ")
                    .append(keyValue.getValue()).append(DELIMITER).append(LINE_DELIMITER);
        }
    }

    private void joinResponseBody(StringBuilder responseBuilder) {
        responseBuilder.append(LINE_DELIMITER)
                .append(responseBody);
    }

    public void createOKHttpResponse(HttpResponse response, String responseBody) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(OK);

        ContentType contentType = new ContentType(TEXT_HTML.getName());
        String responseBodyLength = String.valueOf(responseBody.getBytes().length);
        response.setHeader(CONTENT_TYPE, contentType.toString());
        response.setHeader(CONTENT_LENGTH, responseBodyLength);
        response.setResponseBody(responseBody);
    }

    public void createRedirectHttpResponse(HttpResponse response, String location) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(FOUND);
        response.setHeader(LOCATION, location);
    }

    public void createRedirectHttpResponse(HttpResponse response, String location, String authInfo) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(FOUND);
        response.setHeader(LOCATION, location);
        if (authInfo != null) response.setHeader(SET_COOKIE, createCookie(authInfo));
    }

    private String createCookie(String authInfo) {
        return "JSESSIONID=" + authInfo;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
