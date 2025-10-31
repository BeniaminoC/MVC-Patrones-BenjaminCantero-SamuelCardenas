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
 * {@code NavigationManager} gestiona la navegación entre vistas dentro de la aplicación JavaFX.
 *
 * Implementa el patrón Singletonpara garantizar una única instancia
 * responsable de manejar los cambios de escenas y aplicar los estilos CSS correspondientes.
 *
 * Se encarga de:
 * 
 *   Cargar nuevas vistas (FXML) en la escena actual.
 *   Aplicar hojas de estilo (CSS) asociadas a cada vista.
 *   Conservar el estado del {@link Stage} principal.

 */
public class NavigationManager {

    /** Instancia única del gestor de navegación (Singleton). */
    private static NavigationManager instance;

    /** Ventana principal de la aplicación. */
    private Stage primaryStage;

    /** Constructor privado para evitar instanciación directa. */
    private NavigationManager() {}

    /**
     * Retorna la instancia única del {@code NavigationManager}.
     *
     * @return instancia única de NavigationManager
     */
    public static synchronized NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    /**
     * Asigna la ventana principal (Stage) de la aplicación.
     *
     * Debe llamarse una vez al iniciar la aplicación, normalmente desde la clase principal
     * que extiende {@link javafx.application.Application}.
     *
     * @param stage ventana principal de la aplicación
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Cambia la vista actual por una nueva, cargando un archivo FXML y aplicando un CSS.
     *
     * Utiliza el nodo actual (por ejemplo, un botón o campo de texto) para obtener la escena
     * activa y reemplazar su raíz por la nueva vista cargada.
     *
     * @param <T> tipo del controlador asociado a la vista cargada
     * @param node cualquier nodo perteneciente a la escena actual
     * @param fxmlPath ruta del archivo FXML de la nueva vista
     * @param cssPath ruta del archivo CSS a aplicar (puede ser {@code null} o vacío)
     * @return el controlador de la vista cargada
     * @throws RuntimeException si ocurre un error al cargar el FXML o aplicar el CSS
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
     * Inicializa la primera vista de la aplicación al iniciar el programa.
     *
     * Se utiliza al arrancar la aplicación para cargar la vista inicial
     * en el {@link Stage} principal configurado previamente.
     *
     * @param fxmlPath ruta del archivo FXML de la vista inicial
     * @param cssPath ruta del archivo CSS a aplicar (puede ser {@code null} o vacío)
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
