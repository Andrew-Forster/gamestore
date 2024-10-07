package andrewf.UI;

import static andrewf.Colors.*;
import static andrewf.Utils.*;

public class Output {
    public static int buttonCount = 0;
    public static String upperMenuText = "Welcome to the Game Store!";
    public static String lowerMenuText = "";

    public static final String selectColor = color("cyan");

    /**
     * Outputs buttons to the console
     * @param itemsInRow The number of buttons in a row
     * @param selected The selected button
     * @param text The text for the buttons (each string is a button)
     */
    public static void outputButtons(int itemsInRow, int selected, String... text) {
        clearConsole();
        printUpperMenu();
        int count = 0;
        int c1 = 0;
        int c2 = 0;
        buttonCount = text.length;

        while (count < text.length) {
            for (int i = 0; i < itemsInRow; i++) {
                if (c1 == selected) {
                    System.out.print(selectColor + "+----------------------+ " + color("reset"));
                } else {
                    System.out.print("+----------------------+ ");
                }
                c1++;
            }
            System.out.println();

            for (int i = 0; i < itemsInRow; i++) {
                if (count < text.length) {
                    String s = text[count];
                    if (count == selected) {
                        System.out.print(selectColor + "| " + padRight(s, 20) + " | " + color("reset"));
                    } else {
                        System.out.print("| " + padRight(s, 20) + " | ");
                    }
                    count++;
                } else {
                    System.out.print("|                      | ");
                }
            }
            System.out.println();

            for (int i = 0; i < Math.min(itemsInRow, text.length - count + itemsInRow); i++) {
                if (c2 == selected) {
                    System.out.print(selectColor + "+----------------------+ " + color("reset"));
                } else {
                    System.out.print("+----------------------+ ");
                }
                c2++;
            }
            System.out.println();
        }
        printLowerMenu();
    }

    public static void printMainMenu() {
        outputButtons(1, 0, "[U] User", "[A] Admin", "[X] Exit");
    }

    public static void printMainMenu(int selected) {
        outputButtons(1, selected, "[U] User", "[A] Admin", "[X] Exit");
    }

    public static int getButtonCount() {
        return buttonCount;
    }

    public static void printUpperMenu() {
        System.out.println(repeatChar('-', 74));
        System.out.println();
        System.out.println(upperMenuText);
        System.out.println();
        System.out.println(repeatChar('-', 74));
    }

    public static void printLowerMenu() {
        System.out.println("Press [ESC] to go back to main menu");
        System.out.println("Press [ENTER] to select");
        System.out.println("Use arrow keys to navigate");
        // Repeat character 37 times
        System.out.println(repeatChar('-', 74));

        System.out.println("\n" + lowerMenuText);
    }

    public static void printUserAction(int selected) {
        outputButtons(3, selected, "[#] View Products", "[*] Search Products", "[+] Add to Cart", "[-] Remove from Cart",
              "[~] View Cart", "[^] Checkout", "[X] Exit");
    }

    public static void printAdminAction(int selected) {
        outputButtons(3, selected, "[+] Add Product", "[-] Remove Product", "[*] Search Products", "[#] View Products",
              "[~] Update Product", "[^] Save Inv File", "[>] Load Inv File", "[X] Exit");
    }

}
