package web.Controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.Entity.Bid;
import web.Entity.Chat;
import web.Entity.User;
import web.SocketService;

import java.io.IOException;
import java.sql.Timestamp;
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
        if (keysAndValues.get("type").equals("bid")) {
            Bid bid = new Bid();
            bid.setAuctionId(Double.valueOf(String.valueOf(keysAndValues.get("auctionId"))).intValue());
            bid.setAmount(Float.valueOf((String) keysAndValues.get("amount")));
            socketService.sendToAll(new Gson().toJson(bid));

        }

        if (keysAndValues.get("type").equals("chat")) {
            Chat chat = new Chat();
            chat.setId(Double.valueOf(String.valueOf(keysAndValues.get("senderID"))).intValue());
            chat.setMessage((String) keysAndValues.get("message"));
            socketService.sendToAll(new Gson().toJson(chat));

        }

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
