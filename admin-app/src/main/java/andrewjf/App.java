package andrewjf;

import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;
import static andrewjf.Helpers.Colors.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        try {
            client.start("localhost", 8080);
            while (true) {
                System.out.println(colorString("blue", "\n\nWelcome to the Game Store Admin App"));
                System.out.println(colorString("yellow", "GET: ") + "Get all products");
                System.out.println(colorString("yellow", "GET <id>: ") + "Get product by id");
                System.out.println(colorString("yellow", "SEARCH <search term>: ") + "Search for a product");
                System.out.println(colorString("yellow", "POST: ") + "Add a product");
                System.out.println(colorString("yellow", "PUT: ") + "Update a product");
                System.out.println(colorString("yellow", "DELETE <id>: ") + "Delete a product");
                System.out.println(colorString("red", "E: ") + "Exit the program");
                System.out.println("\nEnter a message to send to the server: ");
                String message = scanner.nextLine();

                switch (message) {
                    case "POST":
                        System.out.println("Enter the product name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter the product description: ");
                        String description = scanner.nextLine();
                        System.out.println("Enter the product price: ");
                        double price = scanner.nextDouble();
                        System.out.println("What is this product?");
                        System.out.println("1. Armor");
                        System.out.println("2. Weapon");
                        System.out.println("3. Ability");
                        int type = scanner.nextInt();
                        scanner.nextLine();
                        String createdOn = new Date(System.currentTimeMillis()).toString();

                        switch (type) {
                            case 1:
                                System.out.println("Enter the armor type: ");
                                String armorType = scanner.nextLine();
                                System.out.println("Enter the armor defense: ");
                                int defense = scanner.nextInt();
                                System.out.println("Enter the armor durability: ");
                                int durability = scanner.nextInt();
                                message = "POST " + name + "," + description + "," + price + "," + createdOn + ",Armor,"
                                        + armorType + "," + defense + "," + durability;
                                break;
                            case 2:
                                System.out.println("Enter the weapon damage: ");
                                int damage = scanner.nextInt();
                                System.out.println("Enter the weapon durability: ");
                                int durabilityWeapon = scanner.nextInt();
                                System.out.println("Enter the weapon weight: ");
                                int weight = scanner.nextInt();
                                message = "POST " + name + "," + description + "," + price + "," + createdOn
                                        + ",Weapon," + damage + "," + durabilityWeapon + "," + weight;
                                break;
                            case 3:
                                System.out.println("Enter the ability type: ");
                                String abilityType = scanner.nextLine();
                                System.out.println("Enter the ability cooldown: ");
                                int cooldown = scanner.nextInt();
                                System.out.println("Enter the ability duration: ");
                                int duration = scanner.nextInt();
                                message = "POST " + name + "," + description + "," + price + "," + createdOn
                                        + ",Ability," + abilityType + "," + cooldown + "," + duration;
                                break;
                            default:
                                System.out.println("Invalid type");
                                continue;
                        }
                        break;
                    case "PUT":
                        System.out.println("Enter the product id: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter the product name: ");
                        name = scanner.nextLine();
                        System.out.println("Enter the product description: ");
                        description = scanner.nextLine();
                        System.out.println("Enter the product price: ");
                        price = scanner.nextDouble();
                        System.out.println("What is this product?");
                        System.out.println("1. Armor");
                        System.out.println("2. Weapon");
                        System.out.println("3. Ability");
                        createdOn = new Date(System.currentTimeMillis()).toString();
                        type = scanner.nextInt();
                        scanner.nextLine();

                        switch (type) {
                            case 1:
                                System.out.println("Enter the armor type: ");
                                String armorType = scanner.nextLine();
                                System.out.println("Enter the armor defense: ");
                                int defense = scanner.nextInt();
                                System.out.println("Enter the armor durability: ");
                                int durability = scanner.nextInt();
                                message = "PUT " + id + "," + name + "," + description + "," + price + "," + createdOn + ",Armor,"
                                        + armorType + "," + defense + "," + durability;
                                break;
                            case 2:
                                System.out.println("Enter the weapon damage: ");
                                int damage = scanner.nextInt();
                                System.out.println("Enter the weapon durability: ");
                                int durabilityWeapon = scanner.nextInt();
                                System.out.println("Enter the weapon weight: ");
                                int weight = scanner.nextInt();
                                message = "PUT " + id + "," + name + "," + description + "," + price + "," + createdOn
                                        + ",Weapon," + damage + "," + durabilityWeapon + "," + weight;
                                break;
                            case 3:
                                System.out.println("Enter the ability type: ");
                                String abilityType = scanner.nextLine();
                                System.out.println("Enter the ability cooldown: ");
                                int cooldown = scanner.nextInt();
                                System.out.println("Enter the ability duration: ");
                                int duration = scanner.nextInt();
                                message = "PUT " + id + "," + name + "," + description + "," + price + "," + createdOn
                                        + ",Ability," + abilityType + "," + cooldown + "," + duration;
                                break;
                            default:
                                System.out.println("Invalid type");
                                continue;
                        }
                        break;
                    default:
                        break;
                }

                if (message.equals("e") || message.equals("E")) {
                    break;
                }
                System.out.println(colorString("magenta", client.send(message)));
            }
            scanner.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
