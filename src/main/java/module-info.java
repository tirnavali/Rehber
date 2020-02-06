module RehberSercan {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;

    opens controller to javafx.fxml;

    exports controller;
    exports model;
}
