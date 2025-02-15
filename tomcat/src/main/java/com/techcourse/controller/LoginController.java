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

public class LoginController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String file = FileResolver.resolve("/login.html");
        response.createOKHttpResponse(response, file);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        if (isAuthExist(request)) {
            response.createRedirectHttpResponse(response,"/index.html", parseAuthInfo(request));
            return;
        }

        String account = request.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            String authInfo = saveAuthInfo(user.get());
            response.createRedirectHttpResponse(response,"/index.html", authInfo);
            return;
        }
        response.createRedirectHttpResponse(response,"/401.html", null);
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

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getUri().contains("/login");
    }
}
