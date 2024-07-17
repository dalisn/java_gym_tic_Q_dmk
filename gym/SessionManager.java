package gym;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String, User> activeSessions;
    private static SessionManager instance;

    private SessionManager() {
        activeSessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String createSession(User user) {
        String sessionId = generateSessionId();
        activeSessions.put(sessionId, user);
        return sessionId;
    }

    public User getUserBySessionId(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }

    private String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
}
