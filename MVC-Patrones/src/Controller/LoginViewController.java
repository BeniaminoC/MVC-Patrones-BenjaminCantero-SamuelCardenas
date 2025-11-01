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
 *
 * @author BENJAMIN
 */
public class LoginViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    /**
     * Etiqueta para mostrar mensajes de error, éxito o información.
     */
    @FXML
    private Label errorLabel;

    // Enlace para redirigir al formulario de registro.
    @FXML
    private Hyperlink registerLink;

    /**
     * Sujeto que notifica eventos de autenticación a los observadores.
     */
    private AuthSubject subject;

    private NavigationManager navegador;

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

    // Redirige al formulario de registro de usuario.
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

    private void showInfo(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #6366f1;");
        showMessage();
    }

    //Aplica una animación de aparición gradual (fade-in) al mensaje mostrado. 
    private void showMessage() {
        errorLabel.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), errorLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
