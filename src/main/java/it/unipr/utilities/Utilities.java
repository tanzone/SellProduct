package it.unipr.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class provides utilities to the module, for example the {@code ShowAlert} method that takes as input title, header and content and
 * produces an alert window
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public final class Utilities {
    private Utilities() {
    }

    /**
     * This method allows loading and setting a scene replacing the active scene
     *
     * @param stage the active stage
     * @param file the fxml file to load as next scene
     * @param title the title for the new scene
     * @param c generic instance of T class
     * @param <T> class token used to retrieve resources
     * @return {@code FXMLLoader} - returns an object of class {@code FXMLLOader} based on the fxml file passed as parameter
     * @since 2.0
     */
    public static <T> FXMLLoader loadScene(Stage stage, String file, String title, Class<T> c) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(c.getResource(file + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            return fxmlLoader;
        } catch (IOException exception) {
            System.out.println("Can't open view FXML");
        }
        return new FXMLLoader();
    }

    /**
     * This method generates an alert window based on the parameters
     *
     * @param title used to set the title of the alert window
     * @param header used to set the header of the alert window
     * @param text used to set the content of the alert window
     * @since 2.0
     */
    public static void showAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
