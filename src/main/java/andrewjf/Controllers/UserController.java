package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private AnchorPane productPane;
    @FXML
    private AnchorPane addPane;
    @FXML
    private AnchorPane updatePane;

    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> itemType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clearStackPane();

        try {
            displayProducts(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirect to the main page
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

    /**
     * Display the products in the store
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
        productsPane.setVisible(true);

    }

    /**
     * Search for products based on the search bar
     * and display them
     * @param event
     */
    @FXML
    private void searchProducts(KeyEvent event) {

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
    
    @FXML
    private Label productInfo;


    /**
     * View the product details
     * Opens the product pane
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

        productInfo.setText(info);
        clearStackPane();
        productPane.setVisible(true);
    }

    /**
     * Create a card for the product
     * @param product
     * @return
     */
    private VBox createProductCard(SellableProducts product) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: gray; -fx-padding: 10; -fx-alignment: center;");

        // Product name and price
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label("$" + product.getPrice());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em; -fx-text-fill: black;");
        priceLabel.setStyle("-fx-font-size: 1.2em; -fx-text-fill: black;");

        // Edit button
        Button editButton = new Button("View");
        editButton.setOnAction(event -> {
            viewProduct(product);
        });

        // Add components to the card
        card.getChildren().addAll(nameLabel, priceLabel, editButton);
        return card;
    }

    /**
     * Clear the stack pane
     * @Note: Used to clear the stack pane
     */
    private void clearStackPane() {
        for (int i = 0; i < adminCont.getChildren().size(); i++) {
            adminCont.getChildren().get(i).setVisible(false);
        }
    }

}

