/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

/**
 * Clase principal que inicia la aplicación JavaFX.
 * <p>
 * Esta clase extiende {@link javafx.application.Application} y define el punto de entrada
 * principal para la ejecución de la interfaz gráfica. Se encarga de inicializar el
 * {@link NavigationManager}, establecer la ventana principal y manejar posibles errores
 * durante el arranque de la aplicación.
 * </p>
 *
 * <p>
 * Si ocurre un error crítico durante el inicio, se muestra una alerta al usuario
 * y la aplicación finaliza de forma controlada.
 * </p>
 *
 * @author Benjamin
 */
public class main extends Application {

    /**
     * Método principal de inicio de la aplicación JavaFX.
     * <p>
     * Este método se ejecuta automáticamente al iniciar la aplicación y se encarga de:
     * </p>
     * <ul>
     *   <li>Obtener la instancia del {@link NavigationManager}.</li>
     *   <li>Asignar el {@link javafx.stage.Stage} principal.</li>
     *   <li>Navegar hacia la vista inicial definida en <code>LoginView.fxml</code>.</li>
     * </ul>
     *
     * @param primaryStage Ventana principal (Stage) de la aplicación JavaFX.
     */
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

    /**
     * Muestra un cuadro de diálogo de error y finaliza la aplicación.
     * <p>
     * Se utiliza cuando ocurre un error crítico que impide continuar con la ejecución.
     * </p>
     *
     * @param message Mensaje principal que describe el error.
     * @param e       Excepción que causó el fallo.
     */
    private void showErrorAndExit(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Fatal");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        System.exit(1);
    }

    /**
     * Punto de entrada estándar de la aplicación Java.
     * <p>
     * Llama al método {@link #launch(String...)} de la clase {@link Application},
     * que inicializa y lanza la aplicación JavaFX.
     * </p>
     *
     * @param args Argumentos de línea de comandos (si los hubiera).
     */
    public static void main(String[] args) {
        launch(args);
    }
}

