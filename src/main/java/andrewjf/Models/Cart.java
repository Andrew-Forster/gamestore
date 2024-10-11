package andrewjf.Models;

import java.util.ArrayList;

import andrewjf.Models.Interfaces_Abstract.SellableProducts;

public class Cart {
    
    ArrayList<SellableProducts> userCart = new ArrayList<>();

    public void addProduct(SellableProducts product) {
        userCart.add(product);
    }

    public void removeProduct(SellableProducts product) {
        userCart.remove(product);
    }

    public ArrayList<SellableProducts> getProducts() {
        return userCart;
    }
    
    //TODO: Checkout Functionality
    

}
