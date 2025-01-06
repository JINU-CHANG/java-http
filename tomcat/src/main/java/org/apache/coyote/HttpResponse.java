package org.apache.coyote;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private final List<String> response;

    public HttpResponse () {
        this.response = new ArrayList<>();
    }

    public void setValue(String value) {
        response.add(value);
    }

    public String getHttpResponse() {
        StringBuilder responseBuilder = new StringBuilder();

        for (String value : response) {
            responseBuilder.append(value).append("\r\n");
        }

        return responseBuilder.toString();
    }
}
