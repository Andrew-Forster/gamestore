package andrewf.UI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import andrewf.Controllers.GUIController;
import static andrewf.UI.Buttons.*;

public class GUI extends JFrame implements KeyListener {
    private GUIController controller;

    /**
     * Constructor for the GUI class
     * @param controller the GUIController object
     */
    public GUI(GUIController controller) {
        this.controller = controller;

        setTitle("Game Store");
        setSize(100, 100);
        setLocation(-1000, -1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        addKeyListener(this);
    }

    /**
     * This method is called when a key is pressed
     * @param e the KeyEvent object
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.handleUp();
                break;
            case KeyEvent.VK_DOWN:
                controller.handleDown();
                break;
            case KeyEvent.VK_LEFT:
                controller.handleLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controller.handleRight();
                break;
            case KeyEvent.VK_ENTER:
                controller.handleEnter();
                controller.setButtonCount(getButtonCount());
                break;
            case KeyEvent.VK_ESCAPE:
                controller.handleEscape();
                break;
        }
        controller.updateUI();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
