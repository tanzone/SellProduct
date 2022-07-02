package it.unipr.main;

import it.unipr.accounts.Person;
import it.unipr.controllers.login.LoginController;
import it.unipr.filemanager.FileManager;
import it.unipr.utilities.Utilities;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

import static it.unipr.utilities.Constants.EMPLOYEES_SEL;

/**
 * This class contains the {@code public static void main} of the application
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class Main extends Application {
    /**
     * Main method of the application
     *
     * @param args parameters passed as arguments after launching the software
     * @since 2.0
     */
    public static void main(String[] args) {
        launch();
    }

    //Method that checks if the system administrator has been initialized and if not deletes all configuration files and let's the user set a new admin account
    private boolean checkVirginStart() {
        List<Person> employees = FileManager.readData(EMPLOYEES_SEL, Person.class);

        if (employees.isEmpty()) {
            FileManager.deleteAll();
            return true;
        }
        return false;
    }

    //Method that sets the initial scene, admin signup or login page based on the situation
    private void setScene(Stage stage, String file, String title) {
        Utilities.loadScene(stage, file, "title", LoginController.class);
    }

    @Override
    public void start(Stage stage) {
        if (checkVirginStart())
            this.setScene(stage, "adminCreationView", "Administrator Creation");
        else
            this.setScene(stage, "loginView", "Login");

        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("appIcon.png"))));
        stage.setResizable(false);
        stage.show();
    }
}