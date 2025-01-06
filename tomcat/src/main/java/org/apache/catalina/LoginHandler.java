package org.apache.catalina;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import java.util.Optional;

public class LoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String resource = httpRequest.getURI();
        int index = resource.indexOf("?");
        String path = resource.substring(0, index);
        String queryString = resource.substring(index + 1);

        String[] accountAndPassword = queryString.split("&");
        String account = accountAndPassword[0].split("=")[1];
        String password = accountAndPassword[1].split("=")[1];
        Optional<User> user = InMemoryUserRepository.findByAccount(account);

        System.out.println(user);
        return null;
    }
}
