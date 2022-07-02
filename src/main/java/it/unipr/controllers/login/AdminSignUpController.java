package it.unipr.controllers.login;

import it.unipr.accounts.Person;
import it.unipr.filemanager.FileManager;
import it.unipr.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static it.unipr.utilities.Constants.EMPLOYEES_SEL;

/**
 * This class manages the sign-up scene for the administrator account
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class AdminSignUpController implements Initializable {
    @FXML
    private TextField fieldAdminUser, fieldAdminPassword;

    private boolean checkFields() {
        return !(this.fieldAdminUser.getText().isBlank() || this.fieldAdminPassword.getText().isBlank());
    }

    /**
     * Initialize method of the {@code AdminSignUpController} class
     *
     * @param arg0 URL type parameter
     * @param arg1 ResourceBundle parameter
     * @since 2.0
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    @FXML //ActionEvent that sets the new admin account and changes scene
    private void checkAdmin(ActionEvent actionEvent) {
        if (checkFields()) {
            Person p = new Person(this.fieldAdminUser.getText(), this.fieldAdminPassword.getText());
            List<Person> employees = FileManager.readData(EMPLOYEES_SEL, Person.class);
            employees.add(p);
            FileManager.writeData(employees, EMPLOYEES_SEL);
            Utilities.loadScene((Stage) this.fieldAdminUser.getScene().getWindow(), "loginView", "Login", LoginController.class);
        } else
            Utilities.showAlert("Empty fields", "Empty text fields", "Please insert a username and a password");
    }
}