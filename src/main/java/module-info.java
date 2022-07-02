/**
 * This module contains all the graphical and functional elements for the application
 *
 * @version 2.0
 * @since 2.0
 */
module it.unipr.shopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens it.unipr.main to javafx.fxml;
    exports it.unipr.main;
    opens it.unipr.controllers.login to javafx.fxml;
    exports it.unipr.controllers.login;
    opens it.unipr.accounts to com.google.gson;
    exports it.unipr.accounts;
    opens it.unipr.controllers.user to javafx.fxml;
    exports it.unipr.controllers.user;
    opens it.unipr.controllers.employee to javafx.fxml;
    exports it.unipr.controllers.employee;
    opens it.unipr.warehouse to com.google.gson;
    exports it.unipr.warehouse;
    exports it.unipr.utilities;
    opens it.unipr.utilities to javafx.fxml;
}