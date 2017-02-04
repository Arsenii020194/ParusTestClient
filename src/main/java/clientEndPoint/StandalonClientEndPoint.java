package clientEndPoint;

import UI.Controller;
import UI.Main;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.jpa.entity.Product;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.websocket.*;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Arsenii
 */
@ClientEndpoint
public class StandalonClientEndPoint {

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

        JsonObject recievedObject = Json.createReader(new StringReader(message)).readObject();

        if (recievedObject.getString("action").equals("getAll")){

            Type type = new TypeToken<List<Product>>(){}.getType();
            Gson gson = new Gson();
            Main.allProds = gson.fromJson(recievedObject.getString("objects"), type);
        }

    }

    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    public static void sendMsg(Session session, String msg) throws IOException, EncodeException {
        session.getBasicRemote().sendText(msg);
    }
}
