package web.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.Entity.Bid;
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
        System.out.println(Map.class);
        // Get the value of a key named "firstname"
        Gson gson = new Gson();
        if (keysAndValues.get("type").equals("bid")) {
            Bid bid = new Bid();

            bid.setAuctionId(Double.valueOf(String.valueOf(keysAndValues.get("auctionId"))).intValue());
            bid.setAmount(Float.valueOf((String) keysAndValues.get("amount")));

            JsonElement jsonElement = gson.toJsonTree(bid);
            jsonElement.getAsJsonObject().addProperty("type", "bid");
            socketService.sendToAll(gson.toJson(jsonElement));
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
