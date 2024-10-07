package andrewf.Core.Items;
import andrewf.Core.Interfaces_Abstract.SellableProducts;

public abstract class Weapons extends SellableProducts {
    int damage;
    int durability;
    int weight;

    public Weapons(int id, String name, String description, double price, String createdOn, int damage, int durability, int weight) {
        super(id, name, description, price, createdOn);
        this.damage = damage;
        this.durability = durability;
        this.weight = weight;
    }

}