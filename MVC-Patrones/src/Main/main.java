/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author BENJAMIN
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
        NavigationManager navigator = NavigationManager.getInstance();
        navigator.setPrimaryStage(primaryStage);

        try {
            navigator.navigateFromStart(
                    "/View/LoginView.fxml",
                    "/View/css/loginview.css"
            );
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAndExit("Error al iniciar la aplicación", e);
        }
    }

    private void showErrorAndExit(String message, Exception e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR
        );
        alert.setTitle("Error Fatal");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
