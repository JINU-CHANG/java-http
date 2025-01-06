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
        handlers.put("LOGIN", new LoginHandler());
        this.handlers = handlers;
    }

    public Handler findHandler(HttpRequest httpRequest) {
        if (httpRequest.getMethod().equals("GET")) { // TODO ENUM으로 관리 & 테스트 코드
            if (httpRequest.getURI().matches(".*\\.[a-zA-Z0-9]+$")) {
                return handlers.get("FILE");
            } else if (httpRequest.getURI().startsWith("\\login")) {
                return handlers.get("LOGIN");
            }
        }
        return handlers.get("DEFAULT");
    }
}
