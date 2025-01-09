package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.StatusLine.HTTP_VERSION;
import static org.apache.coyote.http11.StatusLine.STATUS_CODE;
import static org.apache.coyote.http11.StatusLine.STATUS_MESSAGE;

public class RegisterHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String requestBody = httpRequest.getRequestBody();
        String[] values = requestBody.split("&");
        String account = values[0].split("=")[1];
        String password = values[1].split("=")[1];
        String email = values[2].split("=")[1];

        InMemoryUserRepository.save(new User(account, password, email));

        HttpResponse httpResponse = new HttpResponse();
        setStatusLine(httpResponse);
        setHeader(httpResponse, "index.html");
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
        return httpRequest.getMethod().equals("POST") && httpRequest.getURI().endsWith("/register");
    }
}
