package org.apache.catalina;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;

public interface Handler {

    abstract HttpResponse handle(HttpRequest httpRequest);
    abstract boolean canHandle(HttpRequest httpRequest);
}
