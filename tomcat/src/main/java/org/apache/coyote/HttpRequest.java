package org.apache.coyote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.coyote.RequestLine.*;

public class HttpRequest {

    private static final String LINE_DELIMITER = " ";

    private final Map<String, String> request;

    public HttpRequest(InputStream inputStream) throws IOException {
        Map<String, String> request = new HashMap<>();

        boolean requestLineIsSaved = false;
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (!Objects.equals(line = bufferedReader.readLine(), "")) {
            if (!requestLineIsSaved) {
                saveRequestLine(request, line);
                requestLineIsSaved = true;
                continue;
            }

            saveHeaders(request, line);
        }

        this.request = request;
    }

    private void saveRequestLine(Map<String, String> request, String line) {
        String[] values = line.split(LINE_DELIMITER);
        request.put(String.valueOf(METHOD), values[0]);
        request.put(String.valueOf(URI), values[1]);
        request.put(String.valueOf(HTTP_VERSION), values[2]);
    }

    private void saveHeaders(Map<String, String> request, String line) {
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

    public String getHeader(String header) {
        return request.get(header);
    }
}
