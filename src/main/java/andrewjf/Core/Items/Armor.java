package andrewjf.Core.Items;

import andrewjf.Core.Interfaces_Abstract.SellableProducts;


public class Armor extends SellableProducts {
    String type;
    int defense;
    int durability;
    
    public Armor(int id, String name, String description, double price, String createdOn, String type, int defense, int durability) {
        super(id, name, description, price, createdOn);
        this.type = type;
        this.defense = defense;
        this.durability = durability;
    }

    public String getType() {
        return type;
    }

    public int getDefense() {
        return defense;
    }

    public int getDurability() {
        return durability;
    }

}
