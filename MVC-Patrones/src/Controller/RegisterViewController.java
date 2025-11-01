/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import modelo.entity.User;
import modelo.exception.ExistingUserException;
import modelo.exception.ValidationException;
import modelo.service.UserService;
import modelo.observer.AuthSubject;

/**
 *
 * @author BENJAMIN
 */
public class RegisterViewController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Hyperlink loginLink;
    private AuthSubject subject;
    private NavigationManager navegador;
    private UserService servicio;

    @FXML
    public void initialize() {
        subject = AuthSubject.getInstance();
        navegador = NavigationManager.getInstance();
        servicio = new UserService();
        messageLabel.setVisible(false);

        registerButton.setOnAction(e -> {
            handleRegister();
        });
        confirmPasswordField.setOnAction(e -> {
            handleRegister();
        });
        loginLink.setOnAction(e -> handleBackToLogin());
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }

        User provisional;
        try {
            provisional = servicio.registrarUsuario(username, email, password, confirmPassword);
            showSuccess("Creando cuenta...");
            subject.notifyRegister(provisional);
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> navegador.navigateTo(registerButton, "/View/MainView.fxml", "/View/css/mainview.css"));
            pause.play();
        } catch (ValidationException ex) {
            showError(ex.getMessage());
        } catch (ExistingUserException ex) {
            showError(ex.getMessage());
        }

    }

    @FXML
    private void handleBackToLogin() {
        showInfo("Volviendo al inicio de sesión...");
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> navegador.navigateTo(registerButton, "/View/LoginView.fxml", "/View/css/loginview.css"));
        pause.play();
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #ef4444;");
        showMessage();
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #10b981;");
        showMessage();
    }

    private void showInfo(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #6366f1;");
        showMessage();
    }

    private void showMessage() {
        messageLabel.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

}
