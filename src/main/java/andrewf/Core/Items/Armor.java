package andrewf.Core.Items;

import andrewf.Core.SellableProducts;

public abstract class Armor extends SellableProducts {
    String type;
    int defense;
    int durability;
    
    public Armor(int id, String name, String description, double price, String createdOn, String type, int defense, int durability) {
        super(id, name, description, price, createdOn);
        this.type = type;
        this.defense = defense;
        this.durability = durability;
    }

}
