package it.unipr.controllers.employee;

import it.unipr.accounts.Person;
import it.unipr.utilities.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static it.unipr.utilities.Constants.EMPLOYEES_SEL;
import static it.unipr.utilities.Constants.USER_SEL;

/**
 * This class provides all the necessary methods to fill and manage a person row in the employee interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class PersonController {
    @FXML
    private Label username, password;

    private Person person;
    private int type;
    private MyListener myListener;

    @FXML
    private void deleteUser(ActionEvent actionEvent) {
        if (type == USER_SEL)
            this.myListener.clickDeleteUser(this.person.getUsername());
        else if (type == EMPLOYEES_SEL)
            this.myListener.clickDeleteEmployee(this.person.getUsername());
    }


    /**
     * This method sets the data for a person row
     *
     * @param p instance of {@code Person}
     * @param type used to distinguish between users and employees
     * @param myListener check out the see also section
     * @see MyListener
     * @since 2.0
     */
    public void setData(Person p, int type, MyListener myListener) {
        this.person = p;
        this.type = type;
        this.myListener = myListener;

        this.username.setText(p.getUsername());
        this.password.setText(p.getPassword());
    }
}
