package org.apache.catalina.controller;

import com.techcourse.controller.DefaultController;
import com.techcourse.controller.LoginController;
import com.techcourse.controller.RegisterController;
import org.apache.coyote.http11.request.HttpRequest;
import java.util.Map;

public class RequestMapping {

    private RequestMapping() {
    }

    private static final Map<String, Controller> handlers = Map.of(
            "FILE", new FileController(),
            "DEFAULT", new DefaultController(),
            "LOGIN", new LoginController(),
            "REGISTER", new RegisterController());

    public static Controller findController(HttpRequest httpRequest) {
        return handlers.values().stream()
                .filter(handler -> handler.canHandle(httpRequest))
                .findAny()
                .orElse(handlers.get("DEFAULT"));
    }
}
