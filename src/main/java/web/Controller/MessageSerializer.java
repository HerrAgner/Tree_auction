package web.Controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import web.Entity.Bid;

import java.lang.reflect.Type;

public class MessageSerializer implements JsonSerializer<MessageSerializer> {
    public JsonElement serialize(MessageSerializer messageSerializer, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jo = new JsonObject();

        jo.addProperty("className", messageSerializer.getClass().getName());
        // or simply just
        jo.addProperty("className", "Animal");

        // Loop through the animal object's member variables and add them to the JO accordingly

        return jo;
    }
}