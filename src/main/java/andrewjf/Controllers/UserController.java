package andrewjf.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static andrewjf.MainApp.setRoot;

public class UserController implements Initializable {

    @FXML
    private void gotoMain(ActionEvent event) throws IOException {
        setRoot( "main", "Game Store");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
