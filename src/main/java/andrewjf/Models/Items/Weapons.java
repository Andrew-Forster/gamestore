package andrewjf.Models.Items;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;

public class Weapons extends SellableProducts {
    int damage;
    int durability;
    int weight;

    public Weapons(int id, String name, String description, double price, String createdOn, int damage, int durability, int weight) {
        super(id, name, description, price, createdOn);
        this.damage = damage;
        this.durability = durability;
        this.weight = weight;
    }

    public int getDamage() {
        return damage;
    }

    public int getDurability() {
        return durability;
    }

    public int getWeight() {
        return weight;
    }

}