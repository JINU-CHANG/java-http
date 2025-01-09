package org.apache.coyote.http11;

import com.techcourse.exception.UncheckedServletException;
import org.apache.catalina.Handler;
import org.apache.catalina.HandlerMapping;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final HandlerMapping handlerMapping;
    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
        this.handlerMapping = new HandlerMapping();
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(inputStream);
            Handler handler = handlerMapping.findHandler(httpRequest);
            HttpResponse httpResponse = handler.handle(httpRequest);

            outputStream.write(httpResponse.getHttpResponse().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
