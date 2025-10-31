/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import java.io.IOException;

/**
 * Gestor de navegación mejorado
 */
public class NavigationManager {
    
    private static NavigationManager instance;
    private Stage primaryStage;

    private NavigationManager() {}

    public static synchronized NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public <T> T navigateTo(Node node, String fxmlPath, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = node.getScene();
            scene.setRoot(root);
            
            // Aplicar CSS
            scene.getStylesheets().clear();
            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(
                    getClass().getResource(cssPath).toExternalForm()
                );
            }
            
            // Mantener maximizado
            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);
            
            return loader.getController();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al navegar a: " + fxmlPath, e);
        }
    }

    public void navigateFromStart(String fxmlPath, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(
                    getClass().getResource(cssPath).toExternalForm()
                );
            }
            
            primaryStage.setTitle("TaskLink - Gestión de Tareas");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar vista inicial: " + fxmlPath, e);
        }
    }
}
