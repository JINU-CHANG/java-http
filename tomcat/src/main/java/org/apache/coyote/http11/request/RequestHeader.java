package org.apache.coyote.http11.request;

import org.apache.coyote.http11.HttpCookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {

    private static final String LINE_DELIMITER = " ";
    private static final String HEADER_VALUE_DELIMITER = ":";

    private HttpCookie cookie;
    private final Map<String, String> header = new LinkedHashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        saveHeaders(bufferedReader);
    }

    private void saveHeaders(BufferedReader bufferedReader) throws IOException {
        String headerLine = "";
        while (!Objects.equals(headerLine = bufferedReader.readLine(), "") && headerLine != null) {
            String[] values = headerLine.split(LINE_DELIMITER);
            String header = values[0].split(HEADER_VALUE_DELIMITER)[0];
            String headerValue = values[1].trim();

            if (header.equals("Cookie")) { // TODO 리팩토링 고민
                this.cookie = new HttpCookie(headerLine);
                continue;
            }
            this.header.put(header, headerValue);
        }
    }

    public String getHeader(String header) {
        return this.header.get(header);
    }
}
