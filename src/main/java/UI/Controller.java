package UI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import server.jpa.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    public Button categories;
    public Button places;
    public Button money;
    public HBox hBox;
    public Pane panel;
    public VBox mainVbox;
    private VBox vBoxForShow;
    private ToolBar toolBar;
    private Button closeButton;
    private ComboBox locations;
    private ListView listOfCategories;
    private Label label;
    private TextField moneyField;
    private VBox allbyedProducts;
    public static List<Product> products;

    private void addTranslateListener(Node node) {

        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset[0] = node.getParent().getTranslateX() - mouseEvent.getScreenX();
                yOffset[0] = node.getParent().getTranslateY() - mouseEvent.getScreenY();
            }
        });
        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if ((mouseEvent.getSceneY() < panel.getHeight() - 30 && mouseEvent.getSceneY() > 5) && (mouseEvent.getSceneX() < panel.getWidth() - 30 && mouseEvent.getSceneX() > 5)) {
                    node.getParent().setTranslateX(mouseEvent.getScreenX() + xOffset[0]);
                    node.getParent().setTranslateY(mouseEvent.getScreenY() + yOffset[0]);
                }
            }
        });
    }

    public void showPlaces(ActionEvent actionEvent) {

        vBoxForShow = createVbox("Showing locations widget");
        locations = new ComboBox();
        locations.setPrefWidth(177.0);
        locations.setPrefHeight(21.0);
        List<String> productLocations = new ArrayList<>();
        products.forEach(product -> productLocations.add(product.getLocation()));
        List<String> uniqueLocations = productLocations.stream().distinct().collect(Collectors.toList());
        uniqueLocations.add("Show all");
        locations.getItems().addAll(uniqueLocations);
        locations.setPromptText("Locations");
        locations.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1.equals("Show all")) {
                    List<Node> children = panel.getChildren();
                    List<VBox> vBoxes = new ArrayList<>();
                    for (Node child : children) {
                        vBoxes.add((VBox) child);
                    }
                    for (VBox vbox : vBoxes) {

                        switch (vbox.getAccessibleText()) {
                            case "Showing categories widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof ListView) {
                                        ListView list = (ListView) node;
                                        List<String> neededCategories = new ArrayList<>();
                                        products.forEach(product -> {
                                            neededCategories.add(product.getCategory());
                                        });

                                        ObservableList<String> neededCats = FXCollections.observableArrayList(neededCategories.stream().distinct().collect(Collectors.toList()));
                                        list.setItems(neededCats);
                                    }
                                }
                                break;
                            }
                            case "Showing spended money widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof TextField) {
                                        List<Product> neededProducts = new ArrayList<>();
                                        products.forEach(product -> {
                                            neededProducts.add(product);
                                        });

                                        final Long[] sumMoney = {0L};
                                        neededProducts.forEach(product -> sumMoney[0] += product.getPrice());
                                        ((TextField) node).setText(sumMoney[0].toString());
                                    }
                                }
                                break;
                            }
                            case "Showing all byed products widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof ListView) {
                                        ListView list = (ListView) node;
                                        List<String> neededProducts = new ArrayList<>();
                                        products.forEach(product -> {
                                            neededProducts.add(product.toString());
                                        });

                                        ObservableList<String> neededCats = FXCollections.observableArrayList(neededProducts);
                                        list.setItems(neededCats);
                                    }
                                }
                                break;
                            }
                        }
                    }

                } else {
                    List<Node> children = panel.getChildren();
                    List<VBox> vBoxes = new ArrayList<VBox>();
                    for (Node child : children) {
                        vBoxes.add((VBox) child);
                    }
                    for (VBox vbox : vBoxes) {

                        switch (vbox.getAccessibleText()) {
                            case "Showing categories widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof ListView) {
                                        ListView list = (ListView) node;
                                        List<String> neededCategories = new ArrayList<>();
                                        products.forEach(product -> {
                                            if (product.getLocation().equals(t1)) {
                                                neededCategories.add(product.getCategory());
                                            }
                                        });

                                        ObservableList<String> neededCats = FXCollections.observableArrayList(neededCategories.stream().distinct().collect(Collectors.toList()));
                                        list.setItems(neededCats);
                                    }
                                }
                                break;
                            }
                            case "Showing spended money widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof TextField) {
                                        List<Product> neededProducts = new ArrayList<>();
                                        products.forEach(product -> {
                                            if (product.getLocation().equals(t1)) {
                                                neededProducts.add(product);
                                            }
                                        });

                                        final Long[] sumMoney = {0L};
                                        neededProducts.forEach(product -> sumMoney[0] += product.getPrice());
                                        ((TextField) node).setText(sumMoney[0].toString());
                                    }
                                }
                                break;
                            }
                            case "Showing all byed products widget": {
                                for (Node node : vbox.getChildren()) {
                                    if (node instanceof ListView) {
                                        ListView list = (ListView) node;
                                        List<String> neededProducts = new ArrayList<>();
                                        products.forEach(product -> {
                                            if (product.getLocation().equals(t1)) {
                                                neededProducts.add(product.toString());
                                            }
                                        });

                                        ObservableList<String> neededCats = FXCollections.observableArrayList(neededProducts);
                                        list.setItems(neededCats);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });
        vBoxForShow.setPrefHeight(50.0);
        vBoxForShow.getChildren().add(locations);
        vBoxForShow.setVisible(true);

    }

    public void showCategories(ActionEvent actionEvent) {

        vBoxForShow = createVbox("Showing categories widget");
        listOfCategories = new ListView();
        listOfCategories.setEditable(false);
        List<String> productCategories = new ArrayList<>();
        products.forEach(product -> productCategories.add(product.getCategory()));
        ObservableList<String> pr = FXCollections.observableArrayList(productCategories);
        listOfCategories.setItems(pr);
        vBoxForShow.getChildren().add(listOfCategories);

        allbyedProducts = createVbox("Showing all byed products widget");
        ListView allByedProds = new ListView<>();
        List<String> byed = new ArrayList<>();
        products.forEach(product -> byed.add(product.toString()));
        ObservableList<String> byedd = FXCollections.observableArrayList(byed);
        allByedProds.setItems(byedd);
        allbyedProducts.getChildren().add(allByedProds);
        allbyedProducts.setPrefWidth(500.0);

    }

    public void showSpendedMoney(ActionEvent actionEvent) {

        vBoxForShow = createVbox("Showing spended money widget");
        label = new Label("Spended money: ");
        moneyField = new TextField();
        final Long[] sumMoney = {0L};
        products.forEach(product -> sumMoney[0] += product.getPrice());
        moneyField.setText(sumMoney[0].toString());
        moneyField.setEditable(false);
        vBoxForShow.getChildren().add(label);
        vBoxForShow.getChildren().add(moneyField);

    }

    private VBox createVbox(String text) {

        vBoxForShow = new VBox();

        toolBar = new ToolBar();
        closeButton = new Button("X");
        toolBar.getItems().add(closeButton);
        Label title = new Label(text);
        toolBar.getItems().add(title);

        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Button cb = (Button) mouseEvent.getSource();
                HBox hBox = (HBox) cb.getParent();
                ToolBar tb = (ToolBar) hBox.getParent();
                VBox vb = (VBox) tb.getParent();
                vb.setVisible(false);
            }
        });
        toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        vBoxForShow.getChildren().add(toolBar);
        vBoxForShow.setAlignment(Pos.TOP_CENTER);
        vBoxForShow.setBlendMode(BlendMode.SRC_OVER);
        vBoxForShow.setCache(true);
        vBoxForShow.setCacheHint(CacheHint.DEFAULT);
        vBoxForShow.setDepthTest(DepthTest.INHERIT);
        vBoxForShow.setFocusTraversable(true);
        vBoxForShow.setLayoutX(46.0);
        vBoxForShow.setLayoutY(20.0);
        vBoxForShow.setMouseTransparent(false);
        vBoxForShow.setAccessibleText(text);

        vBoxForShow.setPrefWidth(245.0);
        vBoxForShow.getStyleClass().add("vBox");
        panel.getChildren().add(vBoxForShow);
        addTranslateListener(toolBar);

        return vBoxForShow;
    }

}
