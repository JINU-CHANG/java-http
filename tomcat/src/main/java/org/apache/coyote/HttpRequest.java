package org.apache.coyote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.coyote.RequestLine.HTTP_VERSION;
import static org.apache.coyote.RequestLine.METHOD;
import static org.apache.coyote.RequestLine.URI;

public class HttpRequest {

    private static final String LINE_DELIMITER = " ";

    private final Map<RequestLine, String> requestLine = new EnumMap<>(RequestLine.class);
    private final Map<String, String> header = new LinkedHashMap<>();
    private final Map<String, String> queryString = new HashMap<>();

    public HttpRequest(InputStream inputStream) throws IOException {
        boolean requestLineIsSaved = false;
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (!Objects.equals(line = bufferedReader.readLine(), "") && line != null) {
            if (!requestLineIsSaved) {
                saveRequestLine(line);
                requestLineIsSaved = true;
                continue;
            }

            saveHeaders(line);
        }
    }

    private void saveRequestLine(String line) {
        String[] values = line.split(LINE_DELIMITER);
        requestLine.put(METHOD, values[0]);
        requestLine.put(URI, values[1]);
        requestLine.put(HTTP_VERSION, values[2]);

        if (getURI().contains("?")) saveQueryString();
    }

    private void saveQueryString() {
        String queryString = getURI().split("\\?")[1];
        String[] splitedQueryString = queryString.split("&");

        for (String each : splitedQueryString) {
            String[] keyValue = each.split("=");
            this.queryString.put(keyValue[0], keyValue[1]);
        }
    }

    private void saveHeaders(String line) {
        String[] values = line.split(LINE_DELIMITER);
        String header = values[0].split(":")[0];
        String headerValue = values[1].trim();

        this.header.put(header, headerValue);
    }

    public String getMethod() {
       return requestLine.get(METHOD);
    }

    public String getURI() {
        return requestLine.get(URI);
    }

    public String getParameter(String parameter) {
       return queryString.get(parameter);
    }

    public String getHeader(String header) {
        return this.header.get(header);
    }
}
