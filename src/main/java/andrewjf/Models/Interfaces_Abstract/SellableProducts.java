package andrewjf.Models.Interfaces_Abstract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import andrewjf.Models.Items.Ability;
import andrewjf.Models.Items.Armor;
import andrewjf.Models.Items.Weapon;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import javafx.scene.Node;
import javafx.scene.control.Label;


@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Ability.class, name = "Ability"),
        @JsonSubTypes.Type(value = Armor.class, name = "Armor"),
        @JsonSubTypes.Type(value = Weapon.class, name = "Weapon")
})



public abstract class SellableProducts implements Comparable<SellableProducts> {
    int id;
    String name;
    String description;
    double price;
    String createdOn;

    public SellableProducts(int id, String name, String description, double price, String createdOn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "SellableProducts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellableProducts that = (SellableProducts) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return createdOn != null ? createdOn.equals(that.createdOn) : that.createdOn == null;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(SellableProducts o) {
        return this.name.compareTo(o.name);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCreatedOn() {
        return createdOn;
    }
    
    @JsonIgnore
    public String getType() {
        return this.getClass().getSimpleName();
    }
    @JsonIgnore
    public Node getDisplay() {
        return new Label(getName());
    }
    

    
}
