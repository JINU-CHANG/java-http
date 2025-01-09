package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.response.StatusCode.FOUND;

public class RegisterHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String requestBody = httpRequest.getRequestBody();
        String[] values = requestBody.split("&");
        String account = values[0].split("=")[1];
        String password = values[1].split("=")[1];
        String email = values[2].split("=")[1];

        InMemoryUserRepository.save(new User(account, password, email));
        return createHttpResponse("index.html");
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
        return httpRequest.getMethod().equals("POST") && httpRequest.getURI().endsWith("/register");
    }
}
