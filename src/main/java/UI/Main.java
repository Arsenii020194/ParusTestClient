package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ifaces.Manager;
import server.jpa.entity.Product;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.GregorianCalendar;
import java.util.List;

public class Main extends Application {

    @EJB(beanName = "ProductBean")
    private static Manager hb1;

    @Override
    public void start(Stage primaryStage) throws Exception {

      

        List<Product> allProducts = null;

        try {
            InitialContext ctx1 = new InitialContext();
            hb1 = (Manager) ctx1.lookup("ProductBean");
            
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

            hb1.add(product);
            hb1.add(product1);
            hb1.add(product2);
            hb1.add(product3);
            hb1.add(product4);
            hb1.add(product5);
            hb1.add(product6);

            allProducts = hb1.getAll();

        } catch (NamingException ex) {
            ex.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("my.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        Controller.products = allProducts;
    }


    public static void main(String[] args) {

        launch(args);
    }
}
