package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapons;

import static andrewjf.MainApp.setRoot;

public class UserController implements Initializable {

    private static Store store = Store.getInstance();
    private static int currentlySelected = -1;
    private static String currentPane = "products";

    @FXML
    private VBox menu;
    @FXML
    private StackPane adminCont;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
     * TODO: Adjust the stack pane to take up the available space
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
     * @param event Use 'null' to call this function from another function
     * @throws IOException
     */
    @FXML
    private void checkout(ActionEvent event) throws IOException {
        
        double total = store.checkout();
        lblCartPrice.setText("Total: $" + total);
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
        clearStackPane();
        ArrayList<SellableProducts> items = store.getProducts();
        GridPane productCont = new GridPane();
        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }

        for (int i = 0; i < productsPane.getChildren().size(); i++) {
            if (productsPane.getChildren().get(i) instanceof GridPane) {
                productsPane.getChildren().remove(i);
            }
        }

        productsPane.getChildren().add(productCont);
        productCont.setLayoutY(50);
        productCont.setLayoutX(10);
        productsPane.setVisible(true);
        currentPane = "products";

        if (search.getText().length() > 0) {
            searchProducts(null);
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
        ArrayList<SellableProducts> items = store.getCart();
        GridPane productCont = new GridPane();
        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }

        for (int i = 0; i < cartPane.getChildren().size(); i++) {
            if (cartPane.getChildren().get(i) instanceof GridPane) {
                cartPane.getChildren().remove(i);
            }
        }

        cartPane.getChildren().add(productCont);
        productCont.setLayoutY(50);
        productCont.setLayoutX(10);
        cartPane.setVisible(true);

        if (items.size() == 0) {
            Label emptyCart = new Label("Your cart is empty");
            emptyCart.setStyle("-fx-font-size: 2em; -fx-text-fill: white;");
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
            // Remove previous GridPane
            productsPane.getChildren().remove(1);
            productsPane.getChildren().add(productCont);
            productCont.setLayoutY(50);
            productsPane.setVisible(true);
        }

    }

    @FXML
    private Label productInfo;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnRemFromCart;

    /**
     * View the product details
     * Opens the product pane
     * 
     * @param product
     */
    private void viewProduct(SellableProducts product) {
        currentlySelected = product.getId();
        String info = "Name: " + product.getName() + "\n" +
                "ID: " + product.getId() + "\n" +
                "Price: " + product.getPrice() + "\n" +
                "Description: " + product.getDescription();
        if (product instanceof Armor) {
            Armor armor = (Armor) product;
            info += "\nType: " + armor.getType() + "\nDefense: " + armor.getDefense() + "\nDurability: "
                    + armor.getDurability();
        } else if (product instanceof Weapons) {
            Weapons weapon = (Weapons) product;
            info += "\nDamage: " + weapon.getDamage() + "\nDurability: " + weapon.getDurability() + "\nWeight: "
                    + weapon.getWeight();
        } else if (product instanceof Ability) {
            Ability ability = (Ability) product;
            info += "\nType: " + ability.getType() + "\nCooldown: " + ability.getCooldown() + "\nDuration: "
                    + ability.getDuration();
        }

        if (store.getCart().contains(product)) {
            AnchorPane.setRightAnchor(btnAddToCart, 179.2);
            
            btnRemFromCart.setVisible(true);
        } else {
            AnchorPane.setRightAnchor(btnAddToCart, 14.2);
            btnRemFromCart.setVisible(false);
        }

        productInfo.setText(info);
        clearStackPane();
        productPane.setVisible(true);
    }

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




    /**
     * Create a card for the product
     * 
     * @param product
     * @return
     */
    private VBox createProductCard(SellableProducts product) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: #c5d3dd; -fx-padding: 10; -fx-alignment: center;");
        card.setSpacing(10);
        card.setPrefSize(150, 125);

        // Product name and price
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label("$" + product.getPrice());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em; -fx-text-fill: #fff4f4;");
        priceLabel.setStyle("-fx-font-size: 1.2em; -fx-text-fill: #fff4f4;");

        // Edit button
        JFXButton editButton = new JFXButton("View");
        editButton.setStyle("-fx-background-color: #13598b; -fx-text-fill: white;");
        editButton.setOnAction(event -> {
            viewProduct(product);
        });

        // Add components to the card
        card.getChildren().addAll(nameLabel, priceLabel, editButton);
        return card;
    }

    /**
     * Clear the stack pane
     * 
     * @Note: Used to clear the stack pane
     */
    private void clearStackPane() {
        for (int i = 0; i < adminCont.getChildren().size(); i++) {
            adminCont.getChildren().get(i).setVisible(false);
        }
    }

}
