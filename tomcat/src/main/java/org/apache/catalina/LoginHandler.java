package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static org.apache.coyote.http11.HttpHeaderName.SET_COOKIE;
import static org.apache.coyote.http11.response.StatusCode.FOUND;

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String account = httpRequest.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            String authInfo = saveAuthInfo(user.get());
            return createHttpResponse("/index.html", authInfo);
        }
        return createHttpResponse("/401.html", null);
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
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(FOUND);
        httpResponse.setHeader("Location", location);
        if (authInfo != null) httpResponse.setHeader(SET_COOKIE.name, createCookie(authInfo));
        return httpResponse;
    }

    private String createCookie(String authInfo) {
        return "JSESSIONID=" + authInfo;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getURI().contains("/login?");
    }
}
