package org.apache.catalina;

import org.apache.coyote.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private final Map<String, Handler> handlers;

    public HandlerMapping () {
        Map<String, Handler> handlers = new HashMap<>();
        handlers.put("FILE", new FileHandler());
        handlers.put("DEFAULT", new DefaultHandler());
        this.handlers = handlers;
    }

    public Handler findHandler(HttpRequest httpRequest) {
        if (httpRequest.getMethod().equals("GET")) { // TODO ENUM으로 관리 & 테스트 코드
                return handlers.get("FILE");
        }
        return handlers.get("DEFAULT");
    }
}
