package com.techcourse.controller;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.controller.Controller;
import org.apache.catalina.session.Session;
import org.apache.catalina.session.SessionManager;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpHeaderName.SET_COOKIE;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.FOUND;

public class LoginController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        doPost(request, response);
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        if (isAuthExist(request)) {
            createHttpResponse(response,"/index.html", parseAuthInfo(request));
            return;
        }

        String account = request.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            String authInfo = saveAuthInfo(user.get());
            createHttpResponse(response,"/index.html", authInfo);
            return;
        }
        createHttpResponse(response,"/401.html", null);
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

    private void createHttpResponse(HttpResponse response, String location, String authInfo) {
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
        return httpRequest.getUri().contains("/login?");
    }
}
