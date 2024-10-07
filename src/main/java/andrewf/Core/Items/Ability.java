package andrewf.Core.Items;

import andrewf.Core.SellableProducts;

public abstract class Ability extends SellableProducts {
    String type; // Offensive, Defensive, Healing, Buff
    int manaCost;
    int cooldown;
    int range;
    int duration;

    public Ability(int id, String name, String description, double price, String createdOn, String type, int manaCost, int cooldown, int range, int duration) {
        super(id, name, description, price, createdOn);
        this.type = type;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.range = range;
        this.duration = duration;
    }

}
