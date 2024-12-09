package andrewjf.Models.Items;

import andrewjf.Models.Interfaces_Abstract.SellableProducts;


public class Armor extends SellableProducts {
    private String type;
    private int defense;
    private int durability;

    public Armor() {
        super(0, "", "", 0, "");
    }
    
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
