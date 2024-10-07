package andrewf.Controllers;

import static andrewf.UI.Buttons.*;

public class GUIController {
    int selectedButton = 0;
    int buttonsPerRow = 1; // 1 button per row default
    int amountOfButtons = 0;
    StateController ui = new StateController();

    public void updateUI() {
        ui.updateUI(selectedButton);
    }

    public void handleUp() {
        if (selectedButton - buttonsPerRow >= 0) {
            selectedButton -= buttonsPerRow;
        } else {
            selectedButton = (buttonCount - 1) - (buttonsPerRow - (selectedButton % buttonsPerRow)) % buttonsPerRow;
        }
    }

    public void handleDown() {
        if (selectedButton + buttonsPerRow < buttonCount) {
            selectedButton += buttonsPerRow;
        } else {
            selectedButton = selectedButton % buttonsPerRow;
        }
    }

    public void handleLeft() {
        if (selectedButton % buttonsPerRow > 0) {
            selectedButton--;
        } else {
            selectedButton += buttonsPerRow - 1;
        }
    }

    public void handleRight() {
        if (selectedButton % buttonsPerRow < buttonsPerRow - 1 && selectedButton < buttonCount - 1) {
            selectedButton++;
        } else {
            selectedButton -= (selectedButton % buttonsPerRow);
        }
    }

    public void handleEscape() {
        buttonsPerRow = ui.handleEscape();

    }

    public void handleEnter() {
        buttonsPerRow = ui.handleEnter(selectedButton);
    }

    public void setButtonCount(int count) {
        buttonCount = count;
    }
}
