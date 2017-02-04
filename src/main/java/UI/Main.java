package UI;

import clientEndPoint.StandalonClientEndPoint;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.jpa.entity.Product;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

public class Main extends Application {

    public static List<Product> allProds;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Product product = new Product();
        product.setCategory("Meet");
        product.setDate(new GregorianCalendar());
        product.setPrice(500);
        product.setName("Pork");
        product.setLocation("Magnit");
        Product product1 = new Product();
        product1.setCategory("Bread");
        product1.setDate(new GregorianCalendar());
        product1.setPrice(20);
        product1.setName("White");
        product1.setLocation("Aushan");
        Product product2 = new Product();
        product2.setCategory("Eggs");
        product2.setDate(new GregorianCalendar());
        product2.setPrice(20);
        product2.setName("C0");
        product2.setLocation("Aushan");
        Product product3 = new Product();
        product3.setCategory("Backaly");
        product3.setDate(new GregorianCalendar());
        product3.setPrice(456);
        product3.setName("Sausages234");
        product3.setLocation("Atlant");
        Product product4 = new Product();
        product4.setCategory("Backaly");
        product4.setDate(new GregorianCalendar());
        product4.setPrice(456);
        product4.setName("Sausages432");
        product4.setLocation("Atlant");
        Product product5 = new Product();
        product5.setCategory("Backaly");
        product5.setDate(new GregorianCalendar());
        product5.setPrice(456);
        product5.setName("Sausages2222");
        product5.setLocation("Atlant");
        Product product6 = new Product();
        product6.setCategory("Bread");
        product6.setDate(new GregorianCalendar());
        product6.setPrice(456);
        product6.setName("Black");
        product6.setLocation("Aushan");

        Gson gson = new Gson();

        WebSocketContainer container;
        Session session = null;

        try {
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(StandalonClientEndPoint.class, URI.create("ws://localhost:8080/Product"));
            JsonObject message = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product)).build();
            StandalonClientEndPoint.sendMsg(session, message.toString());
            JsonObject message1 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product1)).build();
            StandalonClientEndPoint.sendMsg(session, message1.toString());
            JsonObject message2 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product2)).build();
            StandalonClientEndPoint.sendMsg(session, message2.toString());
            JsonObject message3 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product3)).build();
            StandalonClientEndPoint.sendMsg(session, message3.toString());
            JsonObject message4 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product4)).build();
            StandalonClientEndPoint.sendMsg(session, message4.toString());
            JsonObject message5 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product5)).build();
            StandalonClientEndPoint.sendMsg(session, message5.toString());
            JsonObject message6 = Json.createObjectBuilder().add("action", "add").add("object", gson.toJson(product6)).build();
            StandalonClientEndPoint.sendMsg(session, message6.toString());
            JsonObject message7 = Json.createObjectBuilder().add("action", "getAll").build();
            StandalonClientEndPoint.sendMsg(session, message7.toString());
            Thread.sleep(10000);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                try {

                    session.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }
        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("my.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        Controller.products = Main.allProds;
    }


    public static void main(String[] args) {

        launch(args);
    }
}
