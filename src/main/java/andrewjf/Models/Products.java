package andrewjf.Models;

import java.util.ArrayList;
import java.util.Collections;

import andrewjf.Models.Interfaces_Abstract.SellableProducts;
import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;

public class Products {

    static ArrayList<SellableProducts> products = new ArrayList<>();

    private static int idCounter = 1;

    Armor[] armors = {
            new Armor(generateId(), "Leather Armor", "Light armor made from leather", 50.00, "2021-09-01", "Light", 5,
                    10),
            new Armor(generateId(), "Chainmail Armor", "Medium armor made from chainmail", 100.00, "2021-09-01",
                    "Medium", 10, 15),
            new Armor(generateId(), "Plate Armor", "Heavy armor made from steel plates", 150.00, "2021-09-01", "Heavy",
                    15, 20)
    };

    Weapon[] weapons = {
            new Weapon(generateId(), "Dagger", "A small dagger", 10.00, "2021-09-01", 5, 10, 2),
            new Weapon(generateId(), "Sword", "A standard sword", 20.00, "2021-09-01", 10, 15, 5),
            new Weapon(generateId(), "Greatsword", "A large two-handed sword", 30.00, "2021-09-01", 15, 20, 10)
    };

    Ability[] abilities = {
            new Ability(generateId(), "Fireball", "A fireball spell", 50.00, "2021-09-01", "Fire",  10, 0),
            new Ability(generateId(), "Heal", "A healing spell", 50.00, "2021-09-01", "Heal",  5, 10),
            new Ability(generateId(), "Lightning Bolt", "A lightning bolt spell", 50.00, "2021-09-01", "Lightning", 5, 15)
    };

    public Products() {
        for (Armor armor : armors) {
            products.add(armor);
        }
        for (Weapon weapon : weapons) {
            products.add(weapon);
        }
        for (Ability ability : abilities) {
            products.add(ability);
        }
    }

    public static int generateId() {
        for (SellableProducts product : products) {
            if (product.getId() == idCounter) {
                idCounter++;
            }
        }
        return idCounter++;
    }

    public void setProducts(ArrayList<SellableProducts> products) {
        Products.products = products;
    }

    public void addProduct(SellableProducts product) {
        products.add(product);
    }

    public void removeProduct(SellableProducts product) {
        products.remove(product);
    }

    public void updateProduct(SellableProducts product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
            }
        }
    }

    public ArrayList<SellableProducts> getProducts() {
        return products;
    }

    public ArrayList<SellableProducts> getProducts(String sort) {

        ArrayList<SellableProducts> productsList = new ArrayList<>(products);

        switch (sort) {
            case "name:asc":
                Collections.sort(productsList);
                break;
            case "name:desc":
                Collections.sort(productsList, Collections.reverseOrder());
                break;
            case "price:asc":
                Collections.sort(productsList, (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
                break;
            case "price:desc":
                Collections.sort(productsList, (o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
                break;
        }
        return productsList;
    }


    public SellableProducts getProduct(int id) {
        for (SellableProducts product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<SellableProducts> searchProducts(String name) {
        ArrayList<SellableProducts> searchResults = new ArrayList<>();
        String[] split = name.split(":");
        // Check if has search type
        // id:
        // name:
        // desc:

        if (split.length == 2) {
            String searchType = split[0];
            String searchTerm = split[1];
            for (SellableProducts product : products) {
                switch (searchType) {
                    case "id":
                        if (product.getId() == Integer.parseInt(searchTerm.trim())) {
                            searchResults.add(product);
                        }
                        break;
                    case "name":
                        if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                            searchResults.add(product);
                        }
                        break;
                    case "desc":
                        if (product.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                            searchResults.add(product);
                        }
                        break;
                    case "price":
                        if (product.getPrice() == Double.parseDouble(searchTerm)) {
                            searchResults.add(product);
                        }
                        break;
                }
            }
        } else {
            for (SellableProducts product : products) {
                if ((product.getName().toLowerCase().contains(name.toLowerCase()))
                        || (String.valueOf(product.getId()).contains(name))
                        || (String.valueOf(product.getPrice()).contains(name))
                ) {
                    searchResults.add(product);
                }
            }
            
        }

        

        return searchResults;
    }

}
