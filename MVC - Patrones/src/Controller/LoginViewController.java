/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.ObserverLog;
import Model.SubjectLog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginViewController{
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    @FXML private Hyperlink registerLink;
    private SubjectLog subject;
    private NavigationManager navegador;
    
    @FXML
    public void initialize() {
        subject=SubjectLog.getInstance();
        navegador = NavigationManager.getInstance();
        
        // Ocultar mensaje de error al inicio
        errorLabel.setVisible(false);
        
        // Configurar acciones
        loginButton.setOnAction(e -> handleLogin());
        passwordField.setOnAction(e -> handleLogin());
        registerLink.setOnAction(e -> handleRegister());
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validación básica
        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }
        
        // Prueba
        if (username.equals("admin") && password.equals("1234")) {
            showSuccess("¡Inicio de sesión exitoso!");
            
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> navegador.navigateByRoot(loginButton, "/View/MainView.fxml", "/View/css/mainview.css"));
            pause.play();
        } else {
            showError("Usuario o contraseña incorrectos");
        }
        
        
        
    }
    
    @FXML
    private void handleRegister() {
        showInfo("Abriendo formulario de registro...");
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> navegador.navigateByRoot(loginButton, "/View/RegisterView.fxml", "/View/css/registerview.css"));
            pause.play();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #ef4444;");
        showMessage();
    }
    
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
    
    private void showMessage() {
        errorLabel.setVisible(true);
        
        // Animación de fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), errorLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
    
}
