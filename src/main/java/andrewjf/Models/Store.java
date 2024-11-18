package andrewjf.Models;

import static andrewjf.Models.Products.generateId;

import java.util.ArrayList;

import andrewjf.Helpers.Utils;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;

// import andrewf.Core.Interfaces_Abstract.SellableProducts;

public class Store {
    public static Store instance;
    Products products;
    Cart cart;

    public Store() {
        products = new Products();
        cart = new Cart(); 

        ArrayList<SellableProducts> saved = new ArrayList<>();
        saved = Utils.readFromFile();

        if (saved == null) {
            return;
        }

        products.setProducts(saved);
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

    public ArrayList<SellableProducts> getProducts(String sort) {
        return products.getProducts(sort);
    }

    public SellableProducts getProduct(int id) {
        return products.getProduct(id);
    }

    public ArrayList<SellableProducts> searchProducts(String searchTerm) {
        ArrayList<SellableProducts> searchResults = products.searchProducts(searchTerm);
        return searchResults;
    }

    public void addProduct(SellableProducts product) {
        products.addProduct(product);
    }

    public void setProducts(ArrayList<SellableProducts> products) {
        this.products.setProducts(products);
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

    public void addToCart(SellableProducts product) {
        cart.addProduct(product);
    }

    public void removeFromCart(SellableProducts product) {
        cart.removeProduct(product);
    }

    public ArrayList<SellableProducts> getCart() {
        return cart.getProducts();
    }

    public double checkout() {
        return cart.checkout();
    }
}
