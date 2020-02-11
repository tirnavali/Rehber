module RehberSercan {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires sqlite.jdbc;

    opens controller to javafx.fxml;

    exports controller;
    exports model;
}
