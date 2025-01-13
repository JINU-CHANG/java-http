package com.techcourse.controller;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.FileResolver;
import org.apache.catalina.controller.AbstractController;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.FOUND;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class RegisterController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String uri = request.getUri();
        if (!request.getUri().contains(".")) {
            uri = uri + ".html";
        }

        String file = FileResolver.resolve(uri);
        createGetHttpResponse(response, file);
    }

    private void createGetHttpResponse(HttpResponse response, String file) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(OK);

        String fileString = String.valueOf(file.getBytes().length);
        response.setHeader(CONTENT_TYPE, "text/html;charset=utf-8");
        response.setHeader(CONTENT_LENGTH, fileString);
        response.setResponseBody(file);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String requestBody = request.getRequestBody();
        String[] values = requestBody.split("&");
        String account = values[0].split("=")[1];
        String password = values[1].split("=")[1];
        String email = values[2].split("=")[1];

        InMemoryUserRepository.save(new User(account, password, email));
        createPostHttpResponse(response, "/index.html");
    }

    private void createPostHttpResponse(HttpResponse response, String location) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(FOUND);
        response.setHeader(LOCATION, location);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getUri().contains("/register");
    }
}
