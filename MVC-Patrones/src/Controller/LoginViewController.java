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
 * Gestiona la autenticación del usuario y la navegación hacia la ventana principal o de registro.
 * Implementa la lógica de interacción entre la vista (JavaFX) y los servicios de autenticación del modelo.
 */
public class LoginViewController {

    /** Campo de texto para el nombre de usuario. */
    @FXML private TextField usernameField;

    /** Campo de texto para la contraseña del usuario. */
    @FXML private PasswordField passwordField;

    /** Botón que ejecuta el proceso de inicio de sesión. */
    @FXML private Button loginButton;

    /** Etiqueta para mostrar mensajes de error, éxito o información. */
    @FXML private Label errorLabel;

    /** Enlace para redirigir al formulario de registro. */
    @FXML private Hyperlink registerLink;

    /** Sujeto que notifica eventos de autenticación a los observadores. */
    private AuthSubject subject;

    /** Gestor encargado de la navegación entre vistas. */
    private NavigationManager navegador;

    /** Servicio encargado de la verificación de credenciales de usuario. */
    private UserService verificacion;

    /**
     * Inicializa el controlador y configura los eventos de la interfaz.
     * 
     * Se ejecuta automáticamente cuando la vista se carga.
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
     * Maneja el proceso de autenticación del usuario.
     * 
     * Valida los campos, autentica el usuario mediante {@link UserService},
     * y redirige a la vista principal si las credenciales son correctas.
     *
     * @throws AuthenticationException si las credenciales no son válidas.
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

                javafx.animation.PauseTransition pause =
                        new javafx.animation.PauseTransition(Duration.seconds(2));

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
     */
    @FXML
    private void handleRegister() {
        showInfo("Abriendo formulario de registro...");
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(Duration.seconds(2));

        pause.setOnFinished(event ->
                navegador.navigateTo(loginButton, "/View/RegisterView.fxml", "/View/css/registerview.css"));
        pause.play();
    }

    /**
     * Muestra un mensaje de error en pantalla.
     *
     * @param message texto del error a mostrar
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #ef4444;");
        showMessage();
    }

    /**
     * Muestra un mensaje de éxito en pantalla.
     *
     * @param message texto del mensaje a mostrar
     */
    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #10b981;");
        showMessage();
    }

    /**
     * Muestra un mensaje informativo en pantalla.
     *
     * @param message texto del mensaje a mostrar
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