package org.apache.catalina.handler;

import com.techcourse.handler.DefaultHandler;
import com.techcourse.handler.LoginHandler;
import com.techcourse.handler.RegisterHandler;
import org.apache.coyote.http11.request.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private final Map<String, Handler> handlers;

    public HandlerMapping () {
        Map<String, Handler> handlers = new HashMap<>();
        handlers.put("FILE", new FileHandler());
        handlers.put("DEFAULT", new DefaultHandler());
        handlers.put("LOGIN", new LoginHandler());
        handlers.put("REGISTER", new RegisterHandler());
        this.handlers = handlers;
    }

    public Handler findHandler(HttpRequest httpRequest) {
        return handlers.values().stream()
                .filter(handler -> handler.canHandle(httpRequest))
                .findAny()
                .orElse(handlers.get("DEFAULT"));
    }
}
