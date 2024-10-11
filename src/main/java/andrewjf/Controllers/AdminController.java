package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Date;

import static andrewjf.Core.Products.generateId;
import static andrewjf.MainApp.setRoot;

import andrewjf.Core.Store;
import andrewjf.Core.Interfaces_Abstract.SellableProducts;
import andrewjf.Core.Items.Ability;
import andrewjf.Core.Items.Armor;
import andrewjf.Core.Items.Weapons;

public class AdminController implements Initializable {

    private static Store store = Store.getInstance();

    @FXML
    private VBox menu;

    @FXML
    private StackPane adminCont;

    @FXML
    private AnchorPane products;

    @FXML
    private AnchorPane productPane;

    @FXML
    private TextField search;

    @FXML
    private AnchorPane add;

    @FXML
    private ComboBox<String> itemType;

    /**
     * Initializes
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clearStackPane();

        try {
            displayProducts(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemType.getItems().addAll(
                "Armor",
                "Weapon",
                "Ability");
        prop1.getParent().setVisible(false);
        prop2.getParent().setVisible(false);
        prop3.getParent().setVisible(false);
    }

    @FXML
    private void gotoMain(ActionEvent event) throws IOException {
        setRoot("main", "Game Store");
    }

    @FXML
    private void toggleMenu() {
        menu.setVisible(!menu.isVisible());
        menu.setManaged(menu.isVisible());
    }

    @FXML
    private void displayProducts(ActionEvent event) throws IOException {
        clearStackPane();
        ArrayList<SellableProducts> items = store.getProducts();
        GridPane productCont = new GridPane();
        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }

        for (int i = 0; i < products.getChildren().size(); i++) {
            if (products.getChildren().get(i) instanceof GridPane) {
                products.getChildren().remove(i);
            }
        }

        products.getChildren().add(productCont);
        productCont.setLayoutY(50);
        products.setVisible(true);

    }

    @FXML
    private void addPage(ActionEvent event) {
        clearStackPane();
        add.setVisible(true);
    }

    @FXML
    private void searchProducts(KeyEvent event) {

        ArrayList<SellableProducts> items = store.searchProducts(search.getText());

        GridPane productCont = new GridPane();
        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }
        // Remove previous GridPane
        products.getChildren().remove(1);
        products.getChildren().add(productCont);
        productCont.setLayoutY(50);
        products.setVisible(true);

    }

    // Add product type changing

    @FXML
    private Label prop1;
    @FXML
    private Label prop2;
    @FXML
    private Label prop3;

    @FXML
    private void chooseType(ActionEvent event) {
        String type = itemType.getValue();

        prop1.getParent().setVisible(true);
        prop2.getParent().setVisible(true);
        prop3.getParent().setVisible(true);
        switch (type) {
            case "Armor":
                prop1.setText("Defense");
                prop2.setText("Durability");
                prop3.setText("Weight");
                break;
            case "Weapon":
                prop1.setText("Damage");
                prop2.setText("Durability");
                prop3.setText("Weight");
                break;
            case "Ability":
                prop1.setText("Type");
                prop2.setText("Cooldown");
                prop3.setText("Duration");
                break;
            default:
                prop1.getParent().setVisible(false);
                prop2.getParent().setVisible(false);
                prop3.getParent().setVisible(false);
                break;
        }
    }

    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextArea desc;
    @FXML
    private TextField prop1input;
    @FXML
    private TextField prop2input;
    @FXML
    private TextField prop3input;
    @FXML
    private Label addErr;

    @FXML
    /**
     * Add product to store and display
     * @param event
     */
    private void addProduct(ActionEvent event) {
        String type = itemType.getValue();
        double p = 0;
        String n = name.getText();
        String d = desc.getText();
        String p1 = prop1input.getText();
        String p2 = prop2input.getText();
        String p3 = prop3input.getText();
        int id = generateId();
        String date = new Date(System.currentTimeMillis()).toString();

        addErr.setText((p1.isEmpty() || p2.isEmpty() || p3.isEmpty()) ? "Properties cannot be empty" : addErr.getText());
        addErr.setText(price.getText().isEmpty() ? "Price cannot be empty" : addErr.getText());
        addErr.setText(d.isEmpty() ? "Description cannot be empty" : addErr.getText());
        addErr.setText(n.isEmpty() ? "Name cannot be empty" : addErr.getText());
        if (n.isEmpty() || d.isEmpty() || price.getText().isEmpty() || p1.isEmpty() || p2.isEmpty() || p3.isEmpty()) {
            addErr.setVisible(true);
            return;
        }
        // Needed here to avoid NumberFormatException
        try {
            p = Double.parseDouble(price.getText());
            if (Double.isNaN(p)) {
                addErr.setText("The Price must be a number");
                addErr.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            addErr.setText("Price must be a number");
            addErr.setVisible(true);
            return;
        }


        switch (type) {
            case "Armor":
                // Input validation
                addErr.setText((!p2.matches("[0-9]+")) ? "Durability must be a number" : addErr.getText());
                addErr.setText((!p3.matches("[0-9]+")) ? "Weight must be a number" : addErr.getText());
                if (!p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErr.setVisible(true);
                }

                // Create armor object
                Armor armor = new Armor(id, n, d, p, date, p1, Integer.parseInt(p2), Integer.parseInt(p3));
                store.addProduct(armor);
                break;
            case "Weapon":
                // Input validation
                addErr.setText((!p1.matches("[0-9]+")) ? "Damage must be a number" : addErr.getText());
                addErr.setText((!p2.matches("[0-9]+")) ? "Durability must be a number" : addErr.getText());
                addErr.setText((!p3.matches("[0-9]+")) ? "Weight must be a number" : addErr.getText());
                if (!p1.matches("[0-9]+") || !p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErr.setVisible(true);
                    return;
                }

                // Create weapon object
                Weapons weapon = new Weapons(id, n, d, p, date, Integer.parseInt(p1), Integer.parseInt(p2),
                        Integer.parseInt(p3));
                store.addProduct(weapon);
                break;
            case "Ability":
                // Input validation
                addErr.setText((!p2.matches("[0-9]+")) ? "Cooldown must be a number" : addErr.getText());
                addErr.setText((!p3.matches("[0-9]+")) ? "Duration must be a number" : addErr.getText());
                if (!p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErr.setVisible(true);
                    return;
                }

                // Create ability object
                Ability ability = new Ability(id, n, d, p, date, p1, Integer.parseInt(p2), Integer.parseInt(p3));
                store.addProduct(ability);
                break;
            default:
                break;
        }
        
        addErr.setText("Product added, Redirecting...");
        addErr.setStyle("-fx-text-fill: #40fa39;");
        addErr.setVisible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            addErr.setStyle("-fx-text-fill: #fa3838;");
            addErr.setVisible(false);
    
            try {
                displayProducts(null);
                search.setText("id:" + id);
                searchProducts(null);
            } catch (IOException e) {
                System.out.println("Error displaying products");
                e.printStackTrace();
            }
        }));
        timeline.play();
    }

    

    @FXML
    private Label productInfo;

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
            String info = 
            "Name: " + product.getName() + "\n" + 
            "ID: " + product.getId() + "\n" + 
            "Price: " + product.getPrice() + "\n" + 
            "Description: " + product.getDescription();
            if (product instanceof Armor) {
                Armor armor = (Armor) product;
                info += "\nType: " + armor.getType() + "\nDefense: " + armor.getDefense() + "\nDurability: " + armor.getDurability();
            } else if (product instanceof Weapons) {
                Weapons weapon = (Weapons) product;
                info += "\nDamage: " + weapon.getDamage() + "\nDurability: " + weapon.getDurability() + "\nWeight: " + weapon.getWeight();
            } else if (product instanceof Ability) {
                Ability ability = (Ability) product;
                info += "\nType: " + ability.getType() + "\nCooldown: " + ability.getCooldown() + "\nDuration: " + ability.getDuration();
            }

            productInfo.setText(info);
            clearStackPane();
            productPane.setVisible(true);
        });

        // Add components to the card
        card.getChildren().addAll(nameLabel, priceLabel, editButton);
        return card;
    }

    private void clearStackPane() {
        for (int i = 0; i < adminCont.getChildren().size(); i++) {
            adminCont.getChildren().get(i).setVisible(false);
        }
    }

}
