package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {

    private static final String QUERY_STRING_START_FLAG = "?";
    private static final String QUERY_STRING_START_DELIMITER = "\\?";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String QUERY_STRING_PARAMETER_VALUE_DELIMITER = "=";
    private static final String LINE_DELIMITER = " ";

    private String method;
    private String URI;
    private String httpVersion;
    private final Map<String, String> queryString = new HashMap<>();

    public RequestLine(BufferedReader bufferedReader) throws IOException {
        saveRequestLine(bufferedReader);
    }

    private void saveRequestLine(BufferedReader bufferedReader) throws IOException {
        String[] values = bufferedReader.readLine().split(LINE_DELIMITER);
        this.method = values[0];
        this.URI = values[1];
        this.httpVersion = values[2];

        if (getURI().contains(QUERY_STRING_START_FLAG)) saveQueryString();
    }

    private void saveQueryString() {
        String queryString = getURI().split(QUERY_STRING_START_DELIMITER)[1];
        String[] splitedQueryString = queryString.split(QUERY_STRING_DELIMITER);

        for (String each : splitedQueryString) {
            String[] keyValue = each.split(QUERY_STRING_PARAMETER_VALUE_DELIMITER);
            this.queryString.put(keyValue[0], keyValue[1]);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getURI() {
        return URI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getParameter(String parameter) {
        return queryString.get(parameter);
    }

    public void setURI(String uri) {
        URI = uri;
    }
}
