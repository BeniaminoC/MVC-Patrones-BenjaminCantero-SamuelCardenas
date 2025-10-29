/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.SubjectLog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */

public class RegisterViewController {
    
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Label messageLabel;
    @FXML private Hyperlink loginLink;
    private SubjectLog subject;
    
    @FXML
    public void initialize() {
        subject=SubjectLog.getInstance();
        // Ocultar mensaje al inicio
        messageLabel.setVisible(false);
        
        // Configurar acciones
        registerButton.setOnAction(e -> handleRegister());
        confirmPasswordField.setOnAction(e -> handleRegister());
        loginLink.setOnAction(e -> handleBackToLogin());
    }
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validaciones
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }
        
        if (username.length() < 3) {
            showError("El usuario debe tener al menos 3 caracteres");
            return;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            showError("Por favor, ingresa un email válido");
            return;
        }
        
        if (password.length() < 6) {
            showError("La contraseña debe tener al menos 6 caracteres");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Las contraseñas no coinciden");
            return;
        }
        
        showSuccess("Creando cuenta...");
        try {
            // Cargar la ventana de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
            Parent root = loader.load();
            
            Scene scene = registerButton.getScene();
            scene.setRoot(root);
            
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/View/css/mainview.css").toExternalForm());
            subject.notificarInicioSesion(username);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al cargar la ventana de registro");
        }
        

    }
    
    @FXML
    private void handleBackToLogin() {
        // Aquí cargarías la ventana de login
        showInfo("Volviendo al inicio de sesión...");
        try {
            // Cargar la ventana de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
            Parent root = loader.load();
            
            Scene scene = registerButton.getScene();
            scene.setRoot(root);
            
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/View/css/loginview.css").toExternalForm());
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al cargar la ventana de registro");
        }
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
        
        // Animación de fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
