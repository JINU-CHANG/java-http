package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.util.Optional;

import static org.apache.coyote.http11.response.StatusCode.FOUND;

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String account = httpRequest.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        if (user.isPresent()) {
            return createHttpResponse("/index.html");
        }
        return createHttpResponse("/401.html");
    }

    private HttpResponse createHttpResponse(String location) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(FOUND);
        httpResponse.setHeader("Location", location);
        return httpResponse;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getURI().contains("/login?");
    }
}
