module andrewjf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires java.sql;
    opens andrewjf to javafx.fxml;
    exports andrewjf;
    exports andrewjf.Controllers;
    opens andrewjf.Controllers to javafx.fxml;
}