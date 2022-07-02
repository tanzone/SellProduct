package it.unipr.controllers.login;

import it.unipr.accounts.Person;
import it.unipr.controllers.employee.EmployeeController;
import it.unipr.controllers.user.UserController;
import it.unipr.filemanager.FileManager;
import it.unipr.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static it.unipr.utilities.Constants.*;

/**
 * Controller Class of the login scene, also manages the interactions with the connected scenes.
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @see UserController
 * @see EmployeeController
 * @version 2.0
 * @since 2.0
 */
public class LoginController implements Initializable {
    @FXML
    private ComboBox<String> loginChoiceBox;
    @FXML
    private TextField fieldUser, fieldPassword;

    private boolean admin;

    //Method that returns the admin attribute value
    private boolean getAdmin() {
        return admin;
    }

    //Method that sets the admin attribute value
    private void setAdmin(boolean admin) {
        this.admin = admin;
    }

    //Method that sets the options of the loginChoiceBox
    private void setLoginChoiceBox() {
        this.loginChoiceBox.getItems().addAll(OPTIONS_COMBOBOX_LOGIN);
        this.loginChoiceBox.setStyle("-fx-font-size : 15pt;");
        this.loginChoiceBox.getSelectionModel().selectFirst();
    }

    //Method that checks if the fields are blank or not
    private boolean checkFields() {
        return !(this.fieldUser.getText().isBlank() || this.fieldPassword.getText().isBlank());
    }

    //Method that logs the user in
    private void userLogin(Person p) {
        FXMLLoader loader = Utilities.loadScene((Stage) this.fieldUser.getScene().getWindow(), "userView", "User Menu", UserController.class);
        UserController controller = loader.getController();
        controller.setData(p);
    }

    //Method that logs the employee in
    private void employeeLogin(Person p) {
        FXMLLoader loader = Utilities.loadScene((Stage) this.fieldUser.getScene().getWindow(), "employeeView", "Employee Menu", EmployeeController.class);
        EmployeeController controller = loader.getController();
        controller.setData(p, this.getAdmin());
    }

    //Method that handles user sign up
    private void register(Person p) {
        List<Person> users = FileManager.readData(USER_SEL, Person.class);
        boolean flag = true;

        for (Person u : users)
            if (u.getUsername().equals(p.getUsername())) {
                flag = false;
                break;
            }

        if (flag) {
            users.add(p);
            FileManager.writeData(users, USER_SEL);
            Utilities.showAlert("Success", "Sign-In Successful", "You signed in correctly, and are now being redirected to your user homepage");
            userLogin(p);
        } else
            Utilities.showAlert("Error", "Error during Sign-In", "Username already taken");
    }

    //Method that authenticates the user or employee trying to login
    private boolean authenticate(int choice, Person p) {
        List<Person> users = new ArrayList<>();
        if (choice == USER_LOGIN || choice == USER_SIGN)
            users = FileManager.readData(USER_SEL, Person.class);

        if (choice == EMPLOYEE_LOGIN)
            users = FileManager.readData(EMPLOYEES_SEL, Person.class);

        if (users.isEmpty()) {
            Utilities.showAlert("Error", "Error during Login", "No User Registered");
            return false;
        }
        if (choice == EMPLOYEE_LOGIN && p.equals(users.get(0))) {
            this.setAdmin(true);
            return true;
        }

        for (Person u : users) if (u.equals(p)) return true;

        Utilities.showAlert("Error", "Error during Login", "Wrong username or password");
        return false;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.setAdmin(false);
        this.setLoginChoiceBox();
    }

    @FXML //ActionEvent that controls the scene and calls the needed methods for the specific actions
    private void checkLogin(ActionEvent actionEvent) {
        if (checkFields()) {
            Person p = new Person(this.fieldUser.getText(), this.fieldPassword.getText());
            switch (this.loginChoiceBox.getValue()) {
                case "User Login":
                    if (this.authenticate(USER_LOGIN, p))
                        this.userLogin(p);
                    break;

                case "User Sign-Up":
                    this.register(p);
                    break;

                case "Employee Login":
                    if (this.authenticate(EMPLOYEE_LOGIN, p))
                        employeeLogin(p);
                    break;
            }
        } else
            Utilities.showAlert("Empty fields", "Empty text fields", "Please insert a username and a password");
    }
}