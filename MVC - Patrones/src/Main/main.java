/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author BENJAMIN
 */
public class main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegisterView.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/View/css/loginview.css").toExternalForm());
            //scene.getStylesheets().add(getClass().getResource("/View/css/mainview.css").toExternalForm());
            //scene.getStylesheets().add(getClass().getResource("/View/css/registerview.css").toExternalForm());
            
            primaryStage.setTitle("Registro");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
