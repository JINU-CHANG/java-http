package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import java.util.Optional;

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String account = httpRequest.getParameter("account");
        Optional<User> user = InMemoryUserRepository.findByAccount(account);
        System.out.println(user);
        return null;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequest.getURI().startsWith("\\login");
    }
}
