package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.io.IOException;

public interface Handler {

    abstract HttpResponse handle(HttpRequest httpRequest) throws IOException;
    abstract boolean canHandle(HttpRequest httpRequest);
}
