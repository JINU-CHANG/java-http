package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface Handler {

    abstract HttpResponse handle(HttpRequest httpRequest);
    abstract boolean canHandle(HttpRequest httpRequest);
}
