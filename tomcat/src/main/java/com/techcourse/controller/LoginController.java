package com.techcourse.controller;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.FileResolver;
import org.apache.catalina.controller.AbstractController;
import org.apache.catalina.session.Session;
import org.apache.catalina.session.SessionManager;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpHeaderName.SET_COOKIE;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.FOUND;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String file = FileResolver.resolve("/login.html");
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
        if (isAuthExist(request)) {
            createPostHttpResponse(response,"/index.html", parseAuthInfo(request));
            return;
        }

        String account = request.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            String authInfo = saveAuthInfo(user.get());
            createPostHttpResponse(response,"/index.html", authInfo);
            return;
        }
        createPostHttpResponse(response,"/401.html", null);
    }

    private boolean isAuthExist(HttpRequest httpRequest) {
        String jsessionid = httpRequest.getCookieValue("JSESSIONID");
        if (jsessionid == null) return false;

        Session session = SessionManager.findSession(jsessionid);
        if (session != null) return true;
        return false;
    }

    private String parseAuthInfo(HttpRequest httpRequest) {
        String jsessionid = httpRequest.getCookieValue("JSESSIONID");
        return SessionManager.findSession(jsessionid).getId();
    }

    private String saveAuthInfo(User user) {
        UUID uuid = UUID.randomUUID();
        Session session = new Session(uuid.toString());
        session.setAttribute("user", user);
        SessionManager.add(session);

        return uuid.toString();
    }

    private void createPostHttpResponse(HttpResponse response, String location, String authInfo) {
        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(FOUND);
        response.setHeader(LOCATION, location);
        if (authInfo != null) response.setHeader(SET_COOKIE, createCookie(authInfo));
    }

    private String createCookie(String authInfo) {
        return "JSESSIONID=" + authInfo;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getUri().contains("/login");
    }
}
