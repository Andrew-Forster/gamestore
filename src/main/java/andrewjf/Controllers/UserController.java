package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import andrewjf.Models.Interfaces_Abstract.SellableProducts;

import static andrewjf.MainApp.setRoot;

public class UserController extends BaseController implements Initializable {

    private static String currentPane = "products";

    @FXML
    private VBox menu;
    @FXML
    private StackPane userCont;
    @FXML
    private JFXDialog confirmDialog;

    // Stack Pane children
    @FXML
    private AnchorPane productsPane;
    @FXML
    private AnchorPane cartPane;
    @FXML
    private AnchorPane productPane;

    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> itemType;

    @FXML
    private Pane resizable;
    @FXML
    private Line line;
    @FXML
    private JFXButton btnSort;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init(userCont, productInfo, productPane, productsPane, search, btnSort, btnAddToCart, btnRemFromCart);
        clearStackPane();
        line.endXProperty().bind(resizable.widthProperty());

        try {
            displayProducts(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirect to the main page
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    private void gotoMain(ActionEvent event) throws IOException {
        setRoot("main", "Game Store");
    }

    /**
     * Toggles the user menu
     * TODO: Add animation
     * ✔️TODO: Adjust the stack pane to take up the available space
     */
    @FXML
    private void toggleMenu() {
        menu.setVisible(!menu.isVisible());
        menu.setManaged(menu.isVisible());
    }

    @FXML
    private Label lblCartPrice;

    /**
     * Checkout the cart and display the total cart price
     * 
     * @param event Use 'null' to call this function from another function
     * @throws IOException
     */
    @FXML
    private void checkout(ActionEvent event) throws IOException {

        double total = store.checkout();
        lblCartPrice.setText("Total: $" + total);
        lblCartPrice.setVisible(true);
        cartPane.setVisible(true);
        currentPane = "cart";
    }

    /**
     * Display the products in the store
     * 
     * @param event Use 'null' to call this function from another function
     * @throws IOException
     */
    @FXML
    private void displayProducts(ActionEvent event) throws IOException {
        currentPane = "products";

        Display();
        
        if (search.getText().length() > 0) {
            searchProducts(null);
        }
    }



    @FXML
    private void sortProducts(ActionEvent event) {
        sortChange();

        if (currentPane == "products") {
            Display();
        } else {
            try {
                displayCart(null);
            } catch (IOException e) {
                System.out.println("Error displaying cart");
                e.printStackTrace();
            }
        }
    }

    /**
     * Display the cart
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    private void displayCart(ActionEvent event) throws IOException {
        lblCartPrice.setVisible(false);
        clearStackPane();
        ArrayList<SellableProducts> items = store.getCart(getSort());
        GridPane productCont = new GridPane();

        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }

        ScrollPane scrollPane = new ScrollPane(productCont);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.getStyleClass().add("scroll-pane");

        productsPane.getChildren().clear();
        productsPane.getChildren().add(scrollPane);
        productsPane.setVisible(true);

        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);

        if (items.size() == 0) {
            Label emptyCart = new Label("Your cart is empty");
            emptyCart.setStyle("-fx-font-size: 2em; -fx-text-fill: white;");
            emptyCart.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));
            productCont.add(emptyCart, 0, 0);
        }
        currentPane = "cart";
    }

    /**
     * Search for products based on the search bar
     * and display them
     * 
     * @param event
     */
    @FXML
    private void searchProducts(KeyEvent event) {

        if (currentPane == "cart") {
            return;
        } else {
            ArrayList<SellableProducts> items = store.searchProducts(search.getText());
            GridPane productCont = new GridPane();

            for (int i = 0; i < items.size(); i++) {
                VBox card = createProductCard(items.get(i));
                productCont.add(card, i % 3, i / 3);
            }

            ScrollPane scrollPane = new ScrollPane(productCont);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);
            scrollPane.getStyleClass().add("scroll-pane");

            productsPane.getChildren().clear();
            productsPane.getChildren().add(scrollPane);
            productsPane.setVisible(true);
        }

    }

    @FXML
    private Label productInfo;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnRemFromCart;


    @FXML
    private void handleCartBack(ActionEvent event) throws IOException {
        if (currentPane.equals("cart")) {
            displayCart(null);
        } else {
            displayProducts(null);
        }
    }

    @FXML
    private Label productResLbl;

    @FXML
    private void addToCart(ActionEvent event) {
        store.addToCart(store.getProduct(currentlySelected));

        productResLbl.setText("Added to cart");
        productResLbl.setStyle("-fx-text-fill: #94f66a;");
        productResLbl.setVisible(true);
        cleanProductResLbl();
    }

    @FXML
    private void remFromCart(ActionEvent event) {
        store.removeFromCart(store.getProduct(currentlySelected));

        productResLbl.setText("Removed from cart");
        productResLbl.setStyle("-fx-text-fill: #fc3737;");
        productResLbl.setVisible(true);
        cleanProductResLbl();
    }

    private void cleanProductResLbl() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            productResLbl.setVisible(false);
            try {
                displayCart(null);
            } catch (IOException e) {
                System.out.println("Error displaying products");
                e.printStackTrace();
            }
        }));
        timeline.play();
    }


}
