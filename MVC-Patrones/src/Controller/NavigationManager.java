/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

/**
 * {@code NavigationManager} gestiona la navegación entre diferentes vistas
 * (escenas) dentro de la aplicación JavaFX.
 * <p>
 * Implementa el patrón de diseño Singleton para asegurar una única instancia
 * global, responsable de cargar archivos FXML, aplicar estilos CSS y mantener
 * la referencia del {@code Stage} principal.
 * </p>
 *
 * <p>
 * <b>Responsabilidades principales:</b></p>
 * <ul>
 * <li>Centralizar la lógica de cambio de escenas.</li>
 * <li>Permitir la carga dinámica de vistas FXML con sus estilos asociados.</li>
 * <li>Administrar el {@code Stage} principal de la aplicación.</li>
 * </ul>
 *
 * @author BENJAMIN
 */
public class NavigationManager {

    /**
     * Instancia única del gestor de navegación (patrón Singleton).
     */
    private static NavigationManager instance;

    /**
     * Ventana principal de la aplicación (Stage raíz).
     */
    private Stage primaryStage;

    /**
     * Constructor privado para evitar instanciación directa.
     * <p>
     * El acceso debe realizarse mediante {@link #getInstance()}.
     * </p>
     */
    private NavigationManager() {
    }

    /**
     * Obtiene la instancia única del {@code NavigationManager}.
     *
     * @return la instancia única del gestor de navegación
     */
    public static synchronized NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    /**
     * Asigna el {@code Stage} principal de la aplicación.
     *
     * @param stage la ventana principal de la aplicación
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Cambia la vista actual dentro de una escena existente.
     * <p>
     * Reemplaza el nodo raíz del {@code Scene} asociado al nodo actual,
     * cargando un nuevo archivo FXML. También permite aplicar una hoja de
     * estilo CSS opcional.
     * </p>
     *
     * @param <T> tipo del controlador asociado al nuevo FXML
     * @param node un nodo perteneciente a la escena actual
     * @param fxmlPath ruta relativa del archivo FXML a cargar
     * @param cssPath ruta relativa del archivo CSS a aplicar (puede ser nula)
     * @return el controlador asociado a la nueva vista cargada
     * @throws RuntimeException si ocurre un error al cargar el FXML
     */
    public <T> T navigateTo(Node node, String fxmlPath, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = node.getScene();
            scene.setRoot(root);

            scene.getStylesheets().clear();
            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(
                        getClass().getResource(cssPath).toExternalForm()
                );
            }

            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);

            return loader.getController();

        } catch (IOException e) {
            throw new RuntimeException("Error al navegar a: " + fxmlPath, e);
        }
    }

    /**
     * Inicia la navegación desde el punto de entrada principal de la
     * aplicación.
     * <p>
     * Carga la vista inicial, aplica el CSS indicado y muestra la ventana
     * principal maximizada con el título "TaskLink - Gestión de Tareas".
     * </p>
     *
     * @param fxmlPath ruta del archivo FXML inicial
     * @param cssPath ruta del archivo CSS inicial (puede ser nula)
     * @throws RuntimeException si ocurre un error al cargar la vista inicial
     */
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
