package andrewjf.Models.Items;
import andrewjf.Models.Interfaces_Abstract.SellableProducts;

public class Ability extends SellableProducts {
    private String type; // Offensive, Defensive, Healing, Buff
    private int cooldown;
    private int duration;

    public Ability() {
        super(0, "", "", 0, "");
    }

    public Ability(int id, String name, String description, double price, String createdOn, String type, int cooldown, int duration) {
        super(id, name, description, price, createdOn);
        this.type = type;
        this.cooldown = cooldown;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDuration() {
        return duration;
    }

}
