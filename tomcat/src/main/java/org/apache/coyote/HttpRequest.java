package org.apache.coyote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private static final int REQUEST_LINE_INDEX = 0;
    private static final String REQUEST_LINE_DELIMITER = " ";

    private final List<String> requests;

    public HttpRequest(InputStream inputStream) throws IOException {
        List<String> requests = new ArrayList<>();
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (!Objects.equals(line = bufferedReader.readLine(), "")) {
            requests.add(line);
        }

        this.requests = requests;
    }

    public String getMethod() { // TODO RequestLine 리팩토링
        String[] requestLine = requests
                .get(REQUEST_LINE_INDEX)
                .split(REQUEST_LINE_DELIMITER);
        return requestLine[0];
    }

    public String getResource() {
        String[] requestLine = requests
                .get(REQUEST_LINE_INDEX)
                .split(REQUEST_LINE_DELIMITER);
        return requestLine[1];
    }
}
