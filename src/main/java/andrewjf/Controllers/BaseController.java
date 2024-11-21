package andrewjf.Controllers;


import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BaseController {
    protected static Store store = Store.getInstance();
    protected static int currentlySelected = -1;
    protected static String sort = "name:asc";

    StackPane cont;
    Label productInfo;
    AnchorPane productPane;
    AnchorPane productsPane;
    TextField search;
    JFXButton btnAddToCart;
    JFXButton btnRemFromCart;
    JFXButton btnSort;

    public void init(StackPane cont, Label productInfo, AnchorPane productPane, AnchorPane productsPane, TextField search, JFXButton btnSort, JFXButton... btns) {
        this.cont = cont;
        this.productInfo = productInfo;
        this.productPane = productPane;
        this.productsPane = productsPane;
        this.search = search;
        this.btnSort = btnSort;

        if (btns.length == 2) {
            btnAddToCart = btns[0];
            btnRemFromCart = btns[1];
        }

    }

    protected void sortChange() {
                switch (btnSort.getText()) {
            case "Sort: Name A -> Z":
                btnSort.setText("Sort: Name Z -> A");
                sort = "name:desc";
                break;
            case "Sort: Name Z -> A":
                btnSort.setText("Sort: Price Low -> High");
                sort = "price:asc";
                break;
            case "Sort: Price Low -> High":
                btnSort.setText("Sort: Price High -> Low");
                sort = "price:desc";
                break;
            case "Sort: Price High -> Low":
                btnSort.setText("Sort: Name A -> Z");
                sort = "name:asc";
                break;  
            default:
                btnSort.setText("Sort: Name A -> Z");
                sort = "name:asc";
                break;
        }
    }

    protected String getSort() {
        return sort;
    }

    protected void Display() {
        clearStackPane();
        ArrayList<SellableProducts> items = store.getProducts(sort);
        GridPane productCont = new GridPane();

        for (int i = 0; i < items.size(); i++) {
            VBox card = createProductCard(items.get(i));
            productCont.add(card, i % 3, i / 3);
        }

        ScrollPane scrollPane = new ScrollPane(productCont);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setStyle("-fx-padding: 10;");

        productsPane.getChildren().clear();
        productsPane.getChildren().add(scrollPane);
        productsPane.setVisible(true);

        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
    }


    /**
     * Create a card for the product
     * 
     * @param product     The product to create the card for
     * @return
     */
protected VBox createProductCard(SellableProducts product) {
    VBox card = new VBox();
    card.setStyle("-fx-border-color: #c5d3dd; -fx-padding: 10; -fx-alignment: center;");
    card.setSpacing(10);
    
    card.setMinSize(140, 125);
    card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    
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

    // Allow card to grow in GridPane
    GridPane.setHgrow(card, Priority.ALWAYS);
    GridPane.setVgrow(card, Priority.ALWAYS);

    return card;
}


    /**
     * View the product details
     * Opens the product pane
     * 
     * @param product     The product to view
     */
    protected void viewProduct(SellableProducts product) {
        currentlySelected = product.getId();
        String info = "Name: " + product.getName() + "\n" +
                "ID: " + product.getId() + "\n" +
                "Price: " + product.getPrice() + "\n" +
                "Description: " + product.getDescription();
        if (product instanceof Armor) {
            Armor armor = (Armor) product;
            info += "\nType: " + armor.getType() + "\nDefense: " + armor.getDefense() + "\nDurability: "
                    + armor.getDurability();
        } else if (product instanceof Weapon) {
            Weapon weapon = (Weapon) product;
            info += "\nDamage: " + weapon.getDamage() + "\nDurability: " + weapon.getDurability() + "\nWeight: "
                    + weapon.getWeight();
        } else if (product instanceof Ability) {
            Ability ability = (Ability) product;
            info += "\nType: " + ability.getType() + "\nCooldown: " + ability.getCooldown() + "\nDuration: "
                    + ability.getDuration();
        }

        if (btnAddToCart != null || btnRemFromCart != null) {
            if (store.getCart().contains(product)) {
                AnchorPane.setRightAnchor(btnAddToCart, 179.2);

                btnRemFromCart.setVisible(true);
            } else {
                AnchorPane.setRightAnchor(btnAddToCart, 14.2);
                btnRemFromCart.setVisible(false);
            }
        }

        productInfo.setText(info);
        clearStackPane();
        productPane.setVisible(true);
    }

    /**
     * Show a dialog with a message
     * 
     * @param msg
     */
    protected void showDialog(String msg) {
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(cont);
        dialog.setContent(new Label(msg));
        dialog.show();
    }

    /**
     * Clear the stack pane
     * 
     * @Note Used to clear the stack pane
     */
    protected void clearStackPane() {
        for (int i = 0; i < cont.getChildren().size(); i++) {
            cont.getChildren().get(i).setVisible(false);
        }
    }
}
