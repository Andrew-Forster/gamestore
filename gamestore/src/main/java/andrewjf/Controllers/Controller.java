package andrewjf.Controllers;
/*
Put header here


 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static andrewjf.MainApp.setRoot;

public class Controller implements Initializable {

    @FXML
    private void gotoAdmin(ActionEvent event) throws IOException {
        setRoot( "adminPage", "Admin Page");
    }

    @FXML
    private void gotoUser(ActionEvent event) throws IOException {
        setRoot( "userPage", "User Page");
    }

    @FXML
    private void gotoMain(ActionEvent event) throws IOException {
        setRoot( "main", "Game Store");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
