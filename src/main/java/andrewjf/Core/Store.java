package andrewjf.Core;

import java.util.ArrayList;

import andrewjf.Core.Interfaces_Abstract.SellableProducts;
import static andrewjf.Core.Products.generateId;

// import andrewf.Core.Interfaces_Abstract.SellableProducts;

public class Store {
    public static Store instance;
    Products products;
    Cart cart;

    public Store() {
        products = new Products();
        cart = new Cart(); // Implement in Milestone 3
    }
    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }
    
    public ArrayList<SellableProducts> getProducts() {
        return products.getProducts();
    }

    public ArrayList<SellableProducts> searchProducts(String searchTerm) {
        ArrayList<SellableProducts> searchResults = products.getProducts(searchTerm);
        return searchResults;
    }

    public void addProduct(SellableProducts product) {
        products.addProduct(product);
    }

    public void removeProduct(SellableProducts product) {
        products.removeProduct(product);
    }

    public void updateProduct(SellableProducts product) {
        products.updateProduct(product);
    }

    public int generateProductId() {
        return generateId();
    }
}
