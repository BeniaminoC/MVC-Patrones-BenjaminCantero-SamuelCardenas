/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
/**
 *
 * @author BENJAMIN
 */
public class NavigationManager {
    private static NavigationManager instance;
    private Stage primaryStage;
    
    private NavigationManager() {}
    
    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }
    
    /**
     * Establece el Stage principal de la aplicación
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    
    /**
     * Navega a una nueva vista usando el método de cambiar Root
     * (Tu método preferido - más eficiente)
     * 
     * @param node Cualquier nodo de la escena actual
     * @param fxmlPath Ruta del archivo FXML
     * @param cssPath Ruta del archivo CSS (puede ser null)
     * @return El controlador de la nueva vista
     */
    public <T> T navigateByRoot(Node node, String fxmlPath, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = node.getScene();
            scene.setRoot(root);
            
            // Limpiar estilos anteriores
            scene.getStylesheets().clear();
            
            // Agregar nuevo estilo si se proporciona
            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            }
            
            // Mantener pantalla completa
            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);
            
            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la vista: " + fxmlPath, e);
        }
    }
    
    /**
     * Navega usando el primaryStage (útil para la primera carga)
     */
    public void navigateFromStart(String fxmlPath, String cssPath) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            }
            
            primaryStage.setTitle("TaskLink");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la vista inicial: " + fxmlPath, e);
        }
    }
    
}
