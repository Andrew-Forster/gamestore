package andrewjf.Helpers;

import java.util.HashMap;

public class Colors {
    // Import using command below
    //--------------------------------
    // import static andrewf.Colors.*;
    //--------------------------------



    // Colors
    private static HashMap<String, String> colors = new HashMap<String, String>();

    static {
        // Colors
        colors.put("red", "\u001B[31m");
        colors.put("green", "\u001B[32m");
        colors.put("yellow", "\u001B[33m");
        colors.put("blue", "\u001B[34m");
        colors.put("magenta", "\u001B[35m");
        colors.put("cyan", "\u001B[36m");
        colors.put("black", "\u001B[30m");
        colors.put("white", "\u001B[37m");
        colors.put("reset", "\u001B[0m");
    }

    /**
     * Get a color
     * @param color Pick: red, green, yellow, blue, magenta, cyan, black, white, reset
     * @return
     */
    public static String color(String color) {
        return colors.get(color);
    }


    /**
     * Color a string
     * 
     * @param color red, green, yellow, blue, magenta, cyan, black, white, reset
     * @param str   string to color
     * @return colored string
     */
    public static String colorString(String color, String str) {
        return colors.get(color) + str + colors.get("reset");
    }

    /**
     * Fail message
     * 
     * @param msg
     * @return colored fail message
     */
    public static String fail(String msg) {
        return colorString("red", "\nX: " + msg);
    }

    /**
     * Info message
     * 
     * @param msg
     * @return colored info message
     */
    public static String info(String msg) {
        return colorString("blue", "\n■ " + msg);
    }

    /**
     * Warn message
     * 
     * @param msg
     * @return colored warn message
     */
    public static String warn(String msg) {
        return colorString("yellow", "\n¡!¡ " + msg);
    }

    /**
     * Success message
     * 
     * @param msg
     * @return colored success message
     */
    public static String success(String msg) {
        return colorString("green", "\n√: " + msg);
    }
}
