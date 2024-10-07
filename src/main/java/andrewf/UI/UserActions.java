package andrewf.UI;

import static andrewf.UI.Buttons.outputButtons;

public class UserActions {
    
    public static void printUserAction(int selected) {
        outputButtons(3, selected, "[#] View Products", "[*] Search Products", "[+] Add to Cart", "[-] Remove from Cart",
              "[~] View Cart", "[^] Checkout", "[X] Exit");
    }
}
