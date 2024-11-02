package andrewjf.Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import andrewjf.Models.Interfaces_Abstract.SellableProducts;

public class Utils {

    /**
     * Pad a string to the left with spaces
     * 
     * @param text
     * @param length
     * @return
     */
    public static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }

    /**
     * Clear the console
     * 
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Repeat a character
     * 
     * @param c the character to repeat
     * @param i the number of times to repeat
     * @return
     */
    public static char[] repeatChar(char c, int i) {
        char[] chars = new char[i];
        Arrays.fill(chars, c);
        
        return chars;
    }

    /**
     * Save products to a file
     * 
     * @param p
     * @return
     */
    public static String saveToFile(SellableProducts... p) {

        PrintWriter pw;

        try {
            File file = new File("products.txt");
            FileWriter fw = new FileWriter(file);
            pw = new PrintWriter(fw);

            ObjectMapper mapper = new ObjectMapper();

            for (SellableProducts product : p) {
                String json = mapper.writeValueAsString(product);
                pw.println(json);
            }
            pw.close();
            return "File Saved!";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage() + "\n\n");
        }

        return "Failed to save to file";
    }

    /**
     * Read products from a file
     * 
     * @return
     */
    public static ArrayList<SellableProducts> readFromFile() {
        ArrayList<SellableProducts> products = new ArrayList<SellableProducts>();

        try {
            File file = new File("products.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String json = sc.nextLine();
                ObjectMapper mapper = new ObjectMapper();
                SellableProducts product = mapper.readValue(json, SellableProducts.class);
                products.add(product);
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage() + "\n\n");
        }

        return products;
    }
}
