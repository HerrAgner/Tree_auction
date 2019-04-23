package web.Controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.Entity.User;
import web.SocketService;

import java.io.IOException;
import java.util.Map;

@Controller
public class SocketController extends TextWebSocketHandler {
    // NOTE: Can not use @Autowired here due to WebSocketConfig instantiating the SocketController
    private SocketService socketService;
    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Received msg: " + message.getPayload());

        // Demonstration purpose only: send back "Hello" + same message as received


        // Example with a generic Map instead of converting the JSON to a specific class
         Map keysAndValues = new Gson().fromJson(message.getPayload(), Map.class);
        // Get the value of a key named "firstname"
         String firstname = (String) keysAndValues.get("firstname");
        String lastname = (String) keysAndValues.get("lastname");
        User user1 = new User();
        user1.setPassword("password1234");
        user1.setEmail("john.doe@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPhoneNumber("(127)-963-1879");

        socketService.sendToAll(new Gson().toJson(user1));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        socketService.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        socketService.removeSession(session);
    }
}
