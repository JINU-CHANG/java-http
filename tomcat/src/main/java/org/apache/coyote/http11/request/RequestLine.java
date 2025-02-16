package org.apache.coyote.http11.request;

import org.apache.coyote.http11.common.HttpMethodName;
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

    private HttpMethodName method;
    private String uri;
    private String httpVersion;
    private final Map<String, String> queryString = new HashMap<>();

    public RequestLine(BufferedReader bufferedReader) throws IOException {
        saveRequestLine(bufferedReader);
    }

    private void saveRequestLine(BufferedReader bufferedReader) throws IOException {
        String[] values = bufferedReader.readLine().split(LINE_DELIMITER);
        this.method = HttpMethodName.from(values[0]);
        this.uri = values[1];
        this.httpVersion = values[2];

        if (isQueryStringExist()) saveQueryString();
    }

    private boolean isQueryStringExist() {
        return getUri().contains(QUERY_STRING_START_FLAG);
    }

    private void saveQueryString() {
        String queryString = getUri().split(QUERY_STRING_START_DELIMITER)[1];
        String[] splitedQueryString = queryString.split(QUERY_STRING_DELIMITER);

        for (String each : splitedQueryString) {
            String[] keyValue = each.split(QUERY_STRING_PARAMETER_VALUE_DELIMITER);
            this.queryString.put(keyValue[0], keyValue[1]);
        }
    }

    public HttpMethodName getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String parameter) {
        return queryString.get(parameter);
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
