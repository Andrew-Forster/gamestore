module andrewjf {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires transitive javafx.graphics;
    requires transitive com.jfoenix;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    

    opens andrewjf to javafx.fxml;
    opens andrewjf.Models.Interfaces_Abstract to com.fasterxml.jackson.databind;
    opens andrewjf.Controllers to javafx.fxml;
    opens andrewjf.Models.Items to com.fasterxml.jackson.databind;

    exports andrewjf;
    exports andrewjf.Controllers;
    exports andrewjf.Models.Interfaces_Abstract to com.fasterxml.jackson.databind;
    exports andrewjf.Models.Items to com.fasterxml.jackson.databind;
}