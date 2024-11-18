package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import andrewjf.Helpers.Utils;
import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;

import java.sql.Date;

import static andrewjf.MainApp.setRoot;
import static andrewjf.Models.Products.generateId;

public class AdminController extends BaseController implements Initializable {

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
    @FXML
    private Pane resizable;
    @FXML
    private Line line;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clearStackPane(adminCont);
        line.endXProperty().bind(resizable.widthProperty());
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
     * Toggles the admin menu
     * TODO: Add animation
     * TODO: Adjust the stack pane to take up the available space
     */
    @FXML
    private void toggleMenu() {
        menu.setVisible(!menu.isVisible());
        menu.setManaged(menu.isVisible());
    }

    @FXML
    private void saveProducts(ActionEvent event) {
        String msg = Utils.saveToFile(store.getProducts().toArray(new SellableProducts[0]));

        // Create dialog saying saved!

        showDialog(msg, adminCont);
    }

    @FXML
    private void loadProducts(ActionEvent event) {
        ArrayList<SellableProducts> products = Utils.readFromFile();
        if (products != null) {
            store.setProducts(products);
            try {
                displayProducts(null);
                showDialog("Products loaded successfully", adminCont);

            } catch (IOException e) {
                e.printStackTrace();
                showDialog("Error loading products", adminCont);
            }
        } else {
            showDialog("Error loading products", adminCont);
        }
    }

    /**
     * Display the products in the store
     * 
     * @param event Use 'null' to call this function from another function
     * @throws IOException
     */
    @FXML
    private void displayProducts(ActionEvent event) throws IOException {
        clearStackPane(adminCont);
        ArrayList<SellableProducts> items = store.getProducts();
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

        if (search.getText().length() > 0) {
            searchProducts(null);
        }
    }

    /**
     * Navigate to the add product page
     * 
     * @param event Use 'null' to call this function from another function
     */
    @FXML
    private void addPage(ActionEvent event) {
        clearStackPane(adminCont);
        addPane.setVisible(true);
    }

    @FXML
    private Label prop1update;
    @FXML
    private Label prop2update;
    @FXML
    private Label prop3update;
    @FXML
    private TextField prop1inputUpdate;
    @FXML
    private TextField prop2inputUpdate;
    @FXML
    private TextField prop3inputUpdate;

    /**
     * Update the product based on the type
     * 
     * @param event
     * @Note For the Update Product Page
     */
    @FXML
    private void updatePage(ActionEvent event) {
        clearStackPane(adminCont);
        updatePane.setVisible(true);

        SellableProducts product = store.getProduct(currentlySelected);
        String type = product.getClass().getSimpleName();

        switch (type) {
            case "Armor":
                prop1update.setText("Type");
                prop2update.setText("Defense");
                prop3update.setText("Durability");

                prop1inputUpdate.setText(((Armor) product).getType());
                prop2inputUpdate.setText(Integer.toString(((Armor) product).getDefense()));
                prop3inputUpdate.setText(Integer.toString(((Armor) product).getDurability()));
                break;
            case "Weapon":
                prop1update.setText("Damage");
                prop2update.setText("Durability");
                prop3update.setText("Weight");

                prop1inputUpdate.setText(Integer.toString(((Weapon) product).getDamage()));
                prop2inputUpdate.setText(Integer.toString(((Weapon) product).getDurability()));
                prop3inputUpdate.setText(Integer.toString(((Weapon) product).getWeight()));
                break;
            case "Ability":
                prop1update.setText("Type");
                prop2update.setText("Cooldown");
                prop3update.setText("Duration");

                prop1inputUpdate.setText(((Ability) product).getType());
                prop2inputUpdate.setText(Integer.toString(((Ability) product).getCooldown()));
                prop3inputUpdate.setText(Integer.toString(((Ability) product).getDuration()));
                break;
        }

        nameUpdate.setText(product.getName());
        priceUpdate.setText(Double.toString(product.getPrice()));
        descUpdate.setText(product.getDescription());

    }

    /**
     * Search for products based on the search bar
     * and display them
     * 
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

        ScrollPane scrollPane = new ScrollPane(productCont);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.getStyleClass().add("scroll-pane");

        productsPane.getChildren().clear();
        productsPane.getChildren().add(scrollPane);
        productsPane.setVisible(true);

    }

    @FXML
    private Label prop1;
    @FXML
    private Label prop2;
    @FXML
    private Label prop3;

    /**
     * Change the properties of the product based on the type
     * 
     * @param event
     * @Note For the Add Product Page
     */
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
     * the products again
     * 
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

        addErr.setText(
                (p1.isEmpty() || p2.isEmpty() || p3.isEmpty()) ? "Properties cannot be empty" : addErr.getText());
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
                Weapon weapon = new Weapon(id, n, d, p, date, Integer.parseInt(p1), Integer.parseInt(p2),
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
    private TextField nameUpdate;
    @FXML
    private TextField priceUpdate;
    @FXML
    private TextArea descUpdate;

    @FXML
    private Label addErrUpdate;

    /**
     * Used to update the product in the store
     * Not for use inside the controller
     * 
     * @param event
     */
    @FXML
    private void updateProduct(ActionEvent event) {
        SellableProducts product = store.getProduct(currentlySelected);
        String type = product.getClass().getSimpleName();
        double p = 0;
        String n = nameUpdate.getText();
        String d = descUpdate.getText();
        String p1 = prop1inputUpdate.getText();
        String p2 = prop2inputUpdate.getText();
        String p3 = prop3inputUpdate.getText();

        addErrUpdate.setText(
                (p1.isEmpty() || p2.isEmpty() || p3.isEmpty()) ? "Properties cannot be empty" : addErrUpdate.getText());
        addErrUpdate.setText(priceUpdate.getText().isEmpty() ? "Price cannot be empty" : addErrUpdate.getText());
        addErrUpdate.setText(d.isEmpty() ? "Description cannot be empty" : addErrUpdate.getText());
        addErrUpdate.setText(n.isEmpty() ? "Name cannot be empty" : addErrUpdate.getText());
        if (n.isEmpty() || d.isEmpty() || priceUpdate.getText().isEmpty() || p1.isEmpty() || p2.isEmpty()
                || p3.isEmpty()) {
            addErrUpdate.setVisible(true);

            return;
        }

        // Needed here to avoid NumberFormatException
        try {
            p = Double.parseDouble(priceUpdate.getText());
            if (Double.isNaN(p)) {
                addErrUpdate.setText("The Price must be a number");
                addErrUpdate.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            addErrUpdate.setText("Price must be a number");
            addErrUpdate.setVisible(true);
            return;
        }

        switch (type) {
            case "Armor":
                // Input validation
                addErrUpdate.setText((!p2.matches("[0-9]+")) ? "Durability must be a number" : addErrUpdate.getText());
                addErrUpdate.setText((!p3.matches("[0-9]+")) ? "Weight must be a number" : addErrUpdate.getText());
                if (!p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErrUpdate.setVisible(true);
                }

                // Create armor object
                Armor armor = new Armor(product.getId(), n, d, p, product.getCreatedOn(), p1, Integer.parseInt(p2),
                        Integer.parseInt(p3));
                store.updateProduct(armor);
                break;
            case "Weapon":
                // Input validation
                addErrUpdate.setText((!p1.matches("[0-9]+")) ? "Damage must be a number" : addErrUpdate.getText());
                addErrUpdate.setText((!p2.matches("[0-9]+")) ? "Durability must be a number" : addErrUpdate.getText());
                addErrUpdate.setText((!p3.matches("[0-9]+")) ? "Weight must be a number" : addErrUpdate.getText());
                if (!p1.matches("[0-9]+") || !p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErrUpdate.setVisible(true);
                    return;
                }

                // Create weapon object
                Weapon weapon = new Weapon(product.getId(), n, d, p, product.getCreatedOn(), Integer.parseInt(p1),
                        Integer.parseInt(p2),
                        Integer.parseInt(p3));
                store.updateProduct(weapon);
                break;
            case "Ability":
                // Input validation
                addErrUpdate.setText((!p2.matches("[0-9]+")) ? "Cooldown must be a number" : addErrUpdate.getText());
                addErrUpdate.setText((!p3.matches("[0-9]+")) ? "Duration must be a number" : addErrUpdate.getText());
                if (!p2.matches("[0-9]+") || !p3.matches("[0-9]+")) {
                    addErrUpdate.setVisible(true);
                    return;
                }

                // Create ability object
                Ability ability = new Ability(product.getId(), n, d, p, product.getCreatedOn(), p1,
                        Integer.parseInt(p2),
                        Integer.parseInt(p3));
                store.updateProduct(ability);
                break;
        }

        addErrUpdate.setText("Product updated, Redirecting...");
        addErrUpdate.setStyle("-fx-text-fill: #40fa39;");
        addErrUpdate.setVisible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            addErrUpdate.setStyle("-fx-text-fill: #fa3838;");
            addErrUpdate.setVisible(false);

            try {
                displayProducts(null);
                search.setText("id:" + product.getId());
                searchProducts(null);
            } catch (IOException e) {
                System.out.println("Error displaying products");
                e.printStackTrace();
            }
        }));
        timeline.play();
    }

    /**
     * Cancel the update and clear the stack pane
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    private void cancelUpdate(ActionEvent event) throws IOException {
        clearStackPane(adminCont);
        viewProduct(store.getProduct(currentlySelected), adminCont, productInfo, productPane);

    }

    /**
     * Opens the confirm dialog to delete the product
     * 
     * @param event
     */
    @FXML
    private void deleteProduct(ActionEvent event) {
        confirmDialog.setVisible(true);
    }

    /**
     * Affirms the deletion of the product
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    private void confirmDelete(ActionEvent event) throws IOException {
        SellableProducts product = store.getProduct(currentlySelected);
        store.removeProduct(product);
        try {
            displayProducts(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        confirmDialog.setVisible(false);

        clearStackPane(adminCont);
        displayProducts(null);
    }

    /**
     * Cancels the deletion of the product
     * 
     * @param event
     */
    @FXML
    private void cancelDelete(ActionEvent event) {
        confirmDialog.setVisible(false);
    }

    @FXML
    private Label productInfo;


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
            viewProduct(product, adminCont, productInfo, productPane);
        });

        // Add components to the card
        card.getChildren().addAll(nameLabel, priceLabel, editButton);
        return card;
    }

}
