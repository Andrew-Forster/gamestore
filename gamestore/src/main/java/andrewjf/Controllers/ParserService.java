package andrewjf.Controllers;

import static andrewjf.Models.Products.generateId;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import andrewjf.Models.Store;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;

public class ParserService {
    protected static Store store = Store.getInstance();

    /*
     * Process's the message and returns a response
     */
    public String processMessage(String message) {
        String[] parts = message.split(" ");

        String command = parts[0];
        Object output = new ArrayList<>();
        String jsonResponse = ""; 
        System.out.println("Command: " + command);
        try {
            switch (command) {
                case "GET":
                    if (parts.length == 1) {
                        output = store.getProducts();
                    } else {
                        int id = Integer.parseInt(parts[1]);
                        output = store.getProduct(id);
                    }
                    break;
                case "SEARCH":
                    String searchTerm = parts[1];
                    output = store.searchProducts(searchTerm);
                    break;
                case "POST":
                    String[] productParts = parts[1].split(",");
                    int id = generateId();
                    String name = productParts[0];
                    String description = productParts[1];
                    double price = Double.parseDouble(productParts[2]);
                    String createdOn = productParts[3];
                    String type = productParts[4];

                    switch (type) {
                        case "Armor":
                            String armorType = productParts[5];
                            int defense = Integer.parseInt(productParts[6]);
                            int durability = Integer.parseInt(productParts[7]);
                            SellableProducts armor = new Armor(id, name, description, price, createdOn, armorType,
                                    defense, durability);
                            store.addProduct(armor);
                            output = armor;
                            break;
                        case "Weapon":
                            int damage = Integer.parseInt(productParts[5]);
                            int durabilityWeapon = Integer.parseInt(productParts[6]);
                            int weight = Integer.parseInt(productParts[7]);
                            SellableProducts weapon = new Weapon(id, name, description, price, createdOn, damage,
                                    durabilityWeapon, weight);
                            store.addProduct(weapon);
                            output = weapon;
                            break;
                        case "Ability":
                            String abilityType = productParts[5];
                            int cooldown = Integer.parseInt(productParts[6]);
                            int duration = Integer.parseInt(productParts[7]);
                            SellableProducts ability = new Ability(id, name, description, price, createdOn, abilityType,
                                    cooldown, duration);
                            store.addProduct(ability);
                            output = ability;
                            break;
                        default:
                            break;
                    }
                    break;
                case "PUT":
                    String[] productPartsEdit = parts[1].split(",");
                    int idEdit = Integer.parseInt(productPartsEdit[0]);
                    String nameEdit = productPartsEdit[1];
                    String descriptionEdit = productPartsEdit[2];
                    double priceEdit = Double.parseDouble(productPartsEdit[3]);
                    String createdOnEdit = productPartsEdit[4];
                    String typeEdit = productPartsEdit[5];

                    switch (typeEdit) {
                        case "Armor":
                            String armorTypeEdit = productPartsEdit[6];
                            int defenseEdit = Integer.parseInt(productPartsEdit[7]);
                            int durabilityEdit = Integer.parseInt(productPartsEdit[8]);
                            SellableProducts armorEdit = new Armor(idEdit, nameEdit, descriptionEdit, priceEdit,
                                    createdOnEdit, armorTypeEdit,
                                    defenseEdit, durabilityEdit);
                            store.updateProduct(armorEdit);
                            output = armorEdit;
                            break;
                        case "Weapon":
                            int damageEdit = Integer.parseInt(productPartsEdit[6]);
                            int durabilityWeaponEdit = Integer.parseInt(productPartsEdit[7]);
                            int weightEdit = Integer.parseInt(productPartsEdit[8]);
                            SellableProducts weaponEdit = new Weapon(idEdit, nameEdit, descriptionEdit, priceEdit,
                                    createdOnEdit, damageEdit,
                                    durabilityWeaponEdit, weightEdit);
                            store.updateProduct(weaponEdit);
                            output = weaponEdit;
                            break;
                        case "Ability":
                            String abilityTypeEdit = productPartsEdit[6];
                            int cooldownEdit = Integer.parseInt(productPartsEdit[7]);
                            int durationEdit = Integer.parseInt(productPartsEdit[8]);
                            SellableProducts abilityEdit = new Ability(idEdit, nameEdit, descriptionEdit, priceEdit,
                                    createdOnEdit, abilityTypeEdit,
                                    cooldownEdit, durationEdit);
                            store.updateProduct(abilityEdit);
                            output = abilityEdit;
                            break;
                        default:
                            break;
                    }
                    break;

                case "DELETE":
                    int idDelete = Integer.parseInt(parts[1]);
                    SellableProducts product = store.getProduct(idDelete);
                    store.removeProduct(product);
                    output = "Product with id " + idDelete + " has been deleted";
                    break;
                default:
                    output = new ArrayList<>();
            }

            // serialize the output list to a JSON string using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            jsonResponse = objectMapper.writeValueAsString(output);

        } catch (Exception e) {
            System.out.println("Error processing message: " + message);
            e.printStackTrace();
            jsonResponse = "[]";
            return jsonResponse;
        }
        return jsonResponse;
    }
}
