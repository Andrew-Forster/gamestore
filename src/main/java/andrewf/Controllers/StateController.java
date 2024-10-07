package andrewf.Controllers;

import java.util.Stack;
import static andrewf.UI.AdminActions.*;
import static andrewf.UI.UserActions.*;
import static andrewf.UI.Buttons.*;

public class StateController {
    public Stack<String> stateStack = new Stack<String>();

    public void push(String state) {
        stateStack.push(state);
    }

    public String pop() {
        return stateStack.pop();
    }

    public String peek() {
        if (stateStack.isEmpty()) {
            return "";
        }
        return stateStack.peek();
    }

    /**
     * Gets the top two elements of the stack
     * 
     * @return Example: "userMenu, viewProducts"
     */
    public String peekTwo() {
        if (stateStack.size() < 2) {
            return "";
        }
        return stateStack.peek() + ", " + stateStack.get(stateStack.size() - 2);
    }

    public void clear() {
        stateStack.clear();
    }

    /**
     * Updates the UI based on the current state
     * 
     * @param selectedButton the currently selected button
     */
    public void updateUI(int selectedButton) {
        switch (peek()) {
            case "":
                printMainMenu(selectedButton);
                break;
            case "userMenu":
                printUserAction(selectedButton);
                break;
            case "adminMenu":
                printAdminAction(selectedButton);
                break;
        }
    }

    /**
     * Handles the enter key based on the current state
     * @param selectedButton the currently selected button
     * @return the number of buttons per row
     * @Note Should only call actions from AdminActions or UserActions
     */
    public int handleEnter(int selectedButton) {
        int buttonsPerRow = 1;
        switch (peek()) {
            case "":
                switch (selectedButton) {
                    case 0:
                        push("userMenu");
                        buttonsPerRow = 3;
                        break;
                    case 1:
                        push("adminMenu");
                        buttonsPerRow = 3;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
                break;
            case "userMenu":
                buttonsPerRow = 3;

                switch (selectedButton) {
                    // case 0:
                    // break;
                    // case 1:
                    // break;
                    // case 2:
                    // break;
                    // case 3:
                    // break;
                    // case 4:
                    // break;
                    // case 5:
                    // break;
                    // case 6:
                    // break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        lowerMenuText = "Selection " + (selectedButton + 1) + " not recognized";
                        break;
                }
                break;
            case "adminMenu":
                buttonsPerRow = 3;

                switch (selectedButton) {
                    // case 0:
                    // break;
                    // case 1:
                    // break;
                    // case 2:
                    // break;
                    // case 3:
                    // break;
                    // case 4:
                    // break;
                    // case 5:
                    // break;
                    // case 6:
                    // break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        lowerMenuText = "Selection " + (selectedButton + 1) + " not recognized";
                        break;
                }
                break;
        }
        return buttonsPerRow;
    }

    public int handleEscape() {
        clear();
        return 1;
    }

}
