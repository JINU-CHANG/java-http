package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import java.util.Optional;

import static org.apache.coyote.http11.StatusLine.HTTP_VERSION;
import static org.apache.coyote.http11.StatusLine.STATUS_CODE;
import static org.apache.coyote.http11.StatusLine.STATUS_MESSAGE;

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String account = httpRequest.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        HttpResponse httpResponse = new HttpResponse();
        setStatusLine(httpResponse);

        if (user.isPresent()) {
            setHeader(httpResponse, "index.html");
        } else {
            setHeader(httpResponse, "401.html");
        }

        return httpResponse;
    }

    private void setStatusLine(HttpResponse httpResponse) {
        httpResponse.setStatusLine(HTTP_VERSION, "HTTP/1.1");
        httpResponse.setStatusLine(STATUS_CODE, "302");
        httpResponse.setStatusLine(STATUS_MESSAGE, "FOUND");
    }

    private void setHeader(HttpResponse httpResponse, String value) {
        httpResponse.setHeader("Location", value);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getURI().contains("/login?");
    }
}
