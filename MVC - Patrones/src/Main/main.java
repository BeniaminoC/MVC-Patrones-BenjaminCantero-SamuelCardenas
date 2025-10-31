/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.NavigationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author BENJAMIN
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        NavigationManager navegador = NavigationManager.getInstance();
        navegador.setPrimaryStage(primaryStage);
        try {
           navegador.navigateFromStart("/View/LoginView.fxml", "/View/css/loginview.css");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
