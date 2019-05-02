package web.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
import java.util.HashMap;
import java.util.List;
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

        Gson gson = new Gson();
        if (keysAndValues.get("type").equals("bid")) {
            Bid bid = new Bid();

            bid.setAuctionId(Double.valueOf(String.valueOf(keysAndValues.get("auctionId"))).intValue());
            bid.setAmount(Float.valueOf((String) keysAndValues.get("amount")));
            bid.setBidderId((String) keysAndValues.get("bidderId"));

            JsonElement jsonElement = gson.toJsonTree(bid);
            jsonElement.getAsJsonObject().addProperty("type", "bid");
            socketService.sendToAll(gson.toJson(jsonElement));
        }

        if (keysAndValues.get("type").equals("chat")) {
            Chat chat = new Chat();
            String receiverID = (String) keysAndValues.get("receiverID");
            String senderID = (String) keysAndValues.get("senderID");

            chat.setReceiver_id(receiverID);
            chat.setSender_id(senderID);
            chat.setMessage((String) keysAndValues.get("message"));
            chat.setChatroom_id((String) keysAndValues.get("chatroomID"));


            JsonElement jsonElement = gson.toJsonTree(chat);
            jsonElement.getAsJsonObject().addProperty("type", "chat");

            HashMap<String, WebSocketSession> Users= socketService.getUserSession();

            for (String i : Users.keySet()) {
                if (i.equals(receiverID)){
                    socketService.sendToOne(Users.get(i) ,new Gson().toJson(jsonElement));
                }
            }
        }

        if (keysAndValues.get("type").equals("login")){
            String email = (String) keysAndValues.get("loginID");
            socketService.addUserIdToSession(email, session);
            HashMap<String, WebSocketSession> Users= socketService.getUserSession();
            for (String i : Users.keySet()) {
                System.out.println("key: " + i + " value: " + Users.get(i));
            }
        }

        if (keysAndValues.get("type").equals("logout")){
            String email = (String) keysAndValues.get("logoutID");
            HashMap<String, WebSocketSession> Users= socketService.getUserSession();
            for (String i : Users.keySet()) {
                if (i.equals(email)) {
                    System.out.println("log out: " + "\nkey: " + i + " value: " + Users.get(i));
                }
            }
            socketService.removeUserSession(email);
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
