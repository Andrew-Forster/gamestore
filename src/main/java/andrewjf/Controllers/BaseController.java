package andrewjf.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BaseController {
    protected static Store store = Store.getInstance();
    protected static int currentlySelected = -1;

    /**
     * Create a card for the product
     * 
     * @param product The product to create the card for
     * @param cont The container to show the product in
     * @param productInfo The label to show the product info
     * @param productPane The anchor pane to show the product pane
     * @return
     */
    protected VBox createProductCard(SellableProducts product, StackPane cont, Label productInfo, AnchorPane productPane) {
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
            viewProduct(product, cont, productInfo, productPane);
        });

        // Add components to the card
        card.getChildren().addAll(nameLabel, priceLabel, editButton);
        return card;
    }

        /**
     * View the product details
     * Opens the product pane
     * 
     * @param product The product to view
     * @param cont The container to show the product in
     * @param productInfo The label to show the product info
     * @param productPane The anchor pane to show the product pane
     */
    protected void viewProduct(SellableProducts product, StackPane cont, Label productInfo, AnchorPane productPane) {
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

        productInfo.setText(info);
        clearStackPane(cont);
        productPane.setVisible(true);
    }

    /**
     * Show a dialog with a message
     * 
     * @param msg
     * @param cont The container to show / attach the dialog to
     */
    protected void showDialog(String msg, StackPane cont) {
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(cont);
        dialog.setContent(new Label(msg));
        dialog.show();
    }

    /**
     * Clear the stack pane
     * 
     * @param cont The stack pane to clear
     * @Note Used to clear the stack pane
     */
    protected void clearStackPane(StackPane cont) {
        for (int i = 0; i < cont.getChildren().size(); i++) {
            cont.getChildren().get(i).setVisible(false);
        }
    }
}
