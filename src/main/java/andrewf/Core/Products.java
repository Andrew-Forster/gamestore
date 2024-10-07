package andrewf.Core;

import java.util.ArrayList;

import andrewf.Core.Interfaces_Abstract.SellableProducts;

public class Products {
    
    ArrayList<SellableProducts> products = new ArrayList<>();

    public void addProduct(SellableProducts product) {
        products.add(product);
    }

    public void removeProduct(SellableProducts product) {
        products.remove(product);
    }

    public ArrayList<SellableProducts> getProducts() {
        return products;
    }

    public SellableProducts getProducts(String name) {
        for (SellableProducts product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    
    public ArrayList<SellableProducts> getProductsByType(String type) {
        ArrayList<SellableProducts> productsByType = new ArrayList<>();
        for (SellableProducts product : products) {
            if (product.getType().equals(type)) {
                productsByType.add(product);
            }
        }
        return productsByType;
    }

    public SellableProducts getProductById(int id) {
        for (SellableProducts product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    
}
