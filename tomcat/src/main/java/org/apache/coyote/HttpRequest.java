package org.apache.coyote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.coyote.RequestLine.HTTP_VERSION;
import static org.apache.coyote.RequestLine.METHOD;
import static org.apache.coyote.RequestLine.URI;

public class HttpRequest {

    private static final String LINE_DELIMITER = " ";

    private final Map<String, String> request = new HashMap<>();
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
        request.put(String.valueOf(METHOD), values[0]);
        request.put(String.valueOf(URI), values[1]);
        request.put(String.valueOf(HTTP_VERSION), values[2]);

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

        request.put(header, headerValue);
    }

    public String getMethod() {
       return request.get(String.valueOf(METHOD));
    }

    public String getURI() {
        return request.get(String.valueOf(URI));
    }

    public String getParameter(String parameter) {
       return queryString.get(parameter);
    }

    public String getHeader(String header) {
        return request.get(header);
    }
}
