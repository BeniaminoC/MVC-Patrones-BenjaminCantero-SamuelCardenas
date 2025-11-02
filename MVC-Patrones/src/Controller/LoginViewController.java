/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import Controller.NavigationManager;
import modelo.entity.User;
import modelo.exception.AuthenticationException;
import modelo.observer.AuthSubject;
import modelo.service.UserService;

/**
 * Controlador para la vista de inicio de sesión (LoginView.fxml).
 *
 * <p>
 * Gestiona la autenticación del usuario, la validación de credenciales y la
 * navegación hacia las vistas principales o de registro. Implementa el patrón
 * MVC como capa de control y usa JavaFX para manejar los eventos de la
 * interfaz.</p>
 *
 * <p>
 * Este controlador interactúa con las clases {@link UserService} para verificar
 * credenciales, {@link AuthSubject} para notificar eventos de autenticación y
 * {@link NavigationManager} para realizar la transición entre escenas.</p>
 *
 * @author BENJAMIN
 */
public class LoginViewController {

    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    @FXML
    private TextField usernameField;

    /**
     * Campo de texto para ingresar la contraseña del usuario.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Botón para ejecutar la acción de inicio de sesión.
     */
    @FXML
    private Button loginButton;

    /**
     * Etiqueta utilizada para mostrar mensajes de error, éxito o información.
     */
    @FXML
    private Label errorLabel;

    /**
     * Enlace para redirigir al formulario de registro de nuevos usuarios.
     */
    @FXML
    private Hyperlink registerLink;

    /**
     * Sujeto que notifica eventos de autenticación a los observadores
     * registrados (por ejemplo, cuando un usuario inicia sesión correctamente).
     */
    private AuthSubject subject;

    /**
     * Gestor de navegación encargado de cambiar entre vistas FXML.
     */
    private NavigationManager navegador;

    /**
     * Servicio que maneja la lógica de autenticación de usuarios.
     */
    private UserService verificacion;

    /**
     * Inicializa el controlador y configura los eventos de la interfaz.
     *
     * <p>
     * Este método se ejecuta automáticamente cuando la vista FXML se carga. Se
     * encargará de inicializar las dependencias, asignar los manejadores de
     * eventos a los botones y enlaces, y preparar los elementos visuales.</p>
     */
    @FXML
    public void initialize() {
        subject = AuthSubject.getInstance();
        navegador = NavigationManager.getInstance();
        verificacion = new UserService();

        errorLabel.setVisible(false);

        loginButton.setOnAction(e -> {
            try {
                handleLogin();
            } catch (AuthenticationException ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        passwordField.setOnAction(e -> {
            try {
                handleLogin();
            } catch (AuthenticationException ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        registerLink.setOnAction(e -> handleRegister());
    }

    /**
     * Maneja el proceso de inicio de sesión de un usuario.
     *
     * <p>
     * Valida los campos de entrada, solicita la autenticación a
     * {@link UserService}, y en caso de éxito, muestra un mensaje y redirige a
     * la vista principal.</p>
     *
     * @throws AuthenticationException si las credenciales son inválidas o no se
     * puede autenticar.
     */
    @FXML
    private void handleLogin() throws AuthenticationException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }

        try {
            User provisional = verificacion.autenticarUsuario(username, password);

            if (provisional instanceof User) {
                showSuccess("¡Inicio de sesión exitoso!");

                subject.notifyLogin(provisional);

                javafx.animation.PauseTransition pause
                        = new javafx.animation.PauseTransition(Duration.seconds(2));

                pause.setOnFinished(event -> {
                    navegador.navigateTo(loginButton, "/View/MainView.fxml", "/View/css/mainview.css");
                    Platform.runLater(() -> subject.notifyLogin(provisional));
                });

                pause.play();
            }

        } catch (AuthenticationException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Redirige al formulario de registro de usuario.
     *
     * <p>
     * Muestra un mensaje informativo y, tras una breve pausa, navega a la vista
     * de registro.</p>
     */
    @FXML
    private void handleRegister() {
        showInfo("Abriendo formulario de registro...");
        javafx.animation.PauseTransition pause
                = new javafx.animation.PauseTransition(Duration.seconds(2));

        pause.setOnFinished(event
                -> navegador.navigateTo(loginButton, "/View/RegisterView.fxml", "/View/css/registerview.css"));
        pause.play();
    }

    /**
     * Muestra un mensaje de error en pantalla.
     *
     * @param message texto del error a mostrar.
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #ef4444;");
        showMessage();
    }

    /**
     * Muestra un mensaje de éxito en pantalla.
     *
     * @param message texto del mensaje a mostrar.
     */
    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #10b981;");
        showMessage();
    }

    /**
     * Muestra un mensaje informativo en pantalla.
     *
     * @param message texto informativo a mostrar.
     */
    private void showInfo(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #6366f1;");
        showMessage();
    }

    /**
     * Aplica una animación de aparición gradual (fade-in) al mensaje mostrado.
     */
    private void showMessage() {
        errorLabel.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), errorLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
