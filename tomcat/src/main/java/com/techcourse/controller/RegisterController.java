package com.techcourse.controller;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.controller.Controller;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpMethodName.POST;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.FOUND;

public class RegisterController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        String requestBody = request.getRequestBody();
        String[] values = requestBody.split("&");
        String account = values[0].split("=")[1];
        String password = values[1].split("=")[1];
        String email = values[2].split("=")[1];

        InMemoryUserRepository.save(new User(account, password, email));
        createHttpResponse(response, "/index.html");
    }

    private void createHttpResponse(HttpResponse response, String location) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(FOUND);
        response.setHeader(LOCATION, location);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getMethod().equals(POST) && httpRequest.getUri().endsWith("/register");
    }
}
