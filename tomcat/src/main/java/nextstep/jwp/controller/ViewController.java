package nextstep.jwp.controller;

import org.apache.coyote.http11.controller.AbstractController;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.HttpStatus;
import org.apache.coyote.http11.response.Response;
import org.apache.coyote.http11.util.Resource;

public class ViewController extends AbstractController {

    @Override
    protected void doGet(Request request, Response response) {

        String fileName = request.getPath();

        response.setStatus(HttpStatus.OK)
                .setContentType(fileName.split("\\.")[1])
                .setResponseBody(Resource.getFile(fileName));
    }
}
