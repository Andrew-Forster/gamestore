package andrewf;

import static andrewf.UI.Buttons.printMainMenu;

import javax.swing.SwingUtilities;

import andrewf.Controllers.GUIController;
import andrewf.UI.GUI;


public class App 
{
    public static void main( String[] args )
    {
        printMainMenu();

        GUIController guiController = new GUIController();

        // Creates and displays the GUI
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(guiController);
            gui.setVisible(true);
            gui.setFocusable(true);
            gui.requestFocusInWindow();
        });
    }
}
