package com.techcourse.handler;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.handler.Handler;
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

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        if (isAuthExist(httpRequest)) {
            return createHttpResponse("/index.html", parseAuthInfo(httpRequest));
        }

        String account = httpRequest.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            String authInfo = saveAuthInfo(user.get());
            return createHttpResponse("/index.html", authInfo);
        }
        return createHttpResponse("/401.html", null);
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

    private HttpResponse createHttpResponse(String location, String authInfo) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpVersion(HTTP_VERSION11);
        httpResponse.setStatusCode(FOUND);
        httpResponse.setHeader(LOCATION, location);
        if (authInfo != null) httpResponse.setHeader(SET_COOKIE, createCookie(authInfo));
        return httpResponse;
    }

    private String createCookie(String authInfo) {
        return "JSESSIONID=" + authInfo;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getUri().contains("/login?");
    }
}
