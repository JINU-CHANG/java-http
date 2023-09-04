package org.apache.coyote.http;

import java.util.HashMap;
import java.util.Map;
import org.apache.catalina.Manager;

public class SessionManager implements Manager {

    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    @Override
    public void add(final HttpSession session) {
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public HttpSession findSession(final String id) {
        return SESSIONS.get(id);
    }

    @Override
    public void remove(final HttpSession session) {
        SESSIONS.remove(session.getId());
    }
}
