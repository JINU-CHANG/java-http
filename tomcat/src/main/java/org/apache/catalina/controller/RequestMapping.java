package org.apache.catalina.controller;

import com.techcourse.controller.DefaultController;
import com.techcourse.controller.LoginController;
import com.techcourse.controller.RegisterController;
import org.apache.coyote.http11.request.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private final Map<String, Controller> handlers;

    public RequestMapping() {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("FILE", new FileController());
        controllers.put("DEFAULT", new DefaultController());
        controllers.put("LOGIN", new LoginController());
        controllers.put("REGISTER", new RegisterController());
        this.handlers = controllers;
    }

    public Controller findServlet(HttpRequest httpRequest) {
        return handlers.values().stream()
                .filter(handler -> handler.canHandle(httpRequest))
                .findAny()
                .orElse(handlers.get("DEFAULT"));
    }
}
