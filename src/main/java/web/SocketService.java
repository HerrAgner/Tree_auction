package web;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SocketService {
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private HashMap<String, WebSocketSession> userSession = new HashMap<>();


    public void sendToOne(WebSocketSession webSocketSession, String message) throws IOException {
        webSocketSession.sendMessage(new TextMessage(message));
    }

    public void sendToOne(WebSocketSession webSocketSession, Object obj, Class klass) throws IOException {
        sendToOne(webSocketSession, new Gson().toJson(obj, klass));
    }

    public void sendToAll(Object obj, Class klass) {
        sendToAll(new Gson().toJson(obj, klass));
    }

    public void sendToAll(String message) {
        TextMessage msg = new TextMessage(message);
        for(WebSocketSession webSocketSession : sessions) {
            try {
                webSocketSession.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    public void addUserIdToSession(String email, WebSocketSession session) {
        this.userSession.put(email, session);
    }

    public HashMap<String, WebSocketSession> getUserSession() {
        return userSession;
    }

    public void removeUserSession(String email) {
        this.userSession.remove(email);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

}
