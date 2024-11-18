package andrewjf.Models;

import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<SellableProducts> getProducts(String sort) {

        ArrayList<SellableProducts> productsList = new ArrayList<>(userCart);

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

    public double checkout() {
        double total = 0;
        for (SellableProducts product : userCart) {
            total += product.getPrice();
        }
        // userCart.clear();
        return total;
    }

}
