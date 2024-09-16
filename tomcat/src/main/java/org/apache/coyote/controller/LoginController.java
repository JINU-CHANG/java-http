package org.apache.coyote.controller;

import org.apache.catalina.AuthManager;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;
import org.apache.coyote.http11.Session;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.apache.coyote.http11.Status.FOUND;
import static org.apache.coyote.http11.Status.OK;

public class LoginController extends AbstractController {

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        try {
            Session session = AuthManager.authenticate(httpRequest.getRequestBody());

            httpResponse.setStatusLine(FOUND);
            httpResponse.setCookie("JSESSIONID", session.getId());
            httpResponse.setLocation("/index.html");
        } catch (IllegalArgumentException exception) {
            httpResponse.setStatusLine(FOUND);
            httpResponse.setLocation("/401.html");
        }

        return httpResponse;
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = new HttpResponse();

        if (httpRequest.getCookie("JSESSIONID") != null && getSession(httpRequest.getCookie("JSESSIONID")) != null) {
            Session session = getSession(httpRequest.getCookie("JSESSIONID"));

            httpResponse.setStatusLine(FOUND);
            httpResponse.setCookie("JSESSIONID", session.getId());
            httpResponse.setLocation("/index.html");

            return httpResponse;
        }

        String responseBody = getResource(httpRequest.getPath() + ".html");

        httpResponse.setStatusLine(OK);
        httpResponse.setContentType(httpRequest.getContentType());
        httpResponse.setResponseBody(responseBody);
        httpResponse.setContentLength(responseBody.getBytes().length);

        return httpResponse;
    }

    private Session getSession(String cookie) {
        return SessionManager.findSession(cookie);
    }

    private String getResource(String path) throws IOException {
        URL resource = getClass().getClassLoader().getResource("static" + path);
        File file = new File(resource.getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
