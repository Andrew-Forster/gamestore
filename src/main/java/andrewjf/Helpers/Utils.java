package andrewjf.Helpers;

import java.util.Arrays;

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
}
