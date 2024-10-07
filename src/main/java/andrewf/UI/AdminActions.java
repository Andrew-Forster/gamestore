package andrewf.UI;

import static andrewf.UI.Buttons.outputButtons;


public class AdminActions {

    public static void printAdminAction(int selected) {
        outputButtons(3, selected, "[+] Add Product", "[-] Remove Product", "[*] Search Products", "[#] View Products",
              "[~] Update Product", "[^] Save Inv File", "[>] Load Inv File", "[X] Exit");
    }

}
