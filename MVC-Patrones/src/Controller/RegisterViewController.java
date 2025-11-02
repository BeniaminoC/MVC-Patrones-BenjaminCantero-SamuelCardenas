/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.application.Platform;

import modelo.entity.User;
import modelo.exception.ExistingUserException;
import modelo.exception.ValidationException;
import modelo.service.UserService;
import modelo.observer.AuthSubject;

/**
 * {@code RegisterViewController} controla la vista de registro de usuarios
 * dentro de la aplicación TaskLink.
 * <p>
 * Se encarga de gestionar la interacción entre el usuario y la lógica de
 * negocio al momento de crear una nueva cuenta. Implementa validaciones,
 * muestra mensajes visuales y coordina la navegación posterior al registro
 * exitoso.
 * </p>
 *
 * <p>
 * <b>Responsabilidades principales:</b></p>
 * <ul>
 * <li>Validar los campos del formulario de registro.</li>
 * <li>Comunicar el resultado al usuario mediante mensajes animados.</li>
 * <li>Invocar los servicios del modelo para registrar un nuevo usuario.</li>
 * <li>Notificar al sistema de autenticación e iniciar la navegación a la vista
 * principal.</li>
 * </ul>
 *
 *
 *
 * @author BENJAMIN
 */
public class RegisterViewController {

    /**
     * Campo de texto para el nombre de usuario.
     */
    @FXML
    private TextField usernameField;

    /**
     * Campo de texto para el correo electrónico.
     */
    @FXML
    private TextField emailField;

    /**
     * Campo para la contraseña del usuario.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Campo para confirmar la contraseña ingresada.
     */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Botón para ejecutar el registro.
     */
    @FXML
    private Button registerButton;

    /**
     * Etiqueta para mostrar mensajes de estado o error.
     */
    @FXML
    private Label messageLabel;

    /**
     * Enlace que permite volver a la pantalla de inicio de sesión.
     */
    @FXML
    private Hyperlink loginLink;

    /**
     * Sujeto observador para notificar el registro exitoso.
     */
    private AuthSubject subject;

    /**
     * Gestor de navegación entre vistas (patrón Singleton).
     */
    private NavigationManager navegador;

    /**
     * Servicio encargado de la lógica de negocio del registro.
     */
    private UserService servicio;

    /**
     * Inicializa la vista de registro al cargarse el FXML.
     * <p>
     * Configura los manejadores de eventos, inicializa los servicios y oculta
     * los mensajes hasta que sean necesarios.
     * </p>
     */
    @FXML
    public void initialize() {
        subject = AuthSubject.getInstance();
        navegador = NavigationManager.getInstance();
        servicio = new UserService();
        messageLabel.setVisible(false);

        registerButton.setOnAction(e -> handleRegister());
        confirmPasswordField.setOnAction(e -> handleRegister());
        loginLink.setOnAction(e -> handleBackToLogin());
    }

    /**
     * Maneja el proceso de registro del usuario.
     * <p>
     * Realiza validaciones de los campos, comunica errores o éxito y, en caso
     * exitoso, crea el usuario, muestra un mensaje y navega a la vista
     * principal.
     * </p>
     */
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validación básica de campos vacíos
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }

        try {
            User provisional = servicio.registrarUsuario(username, email, password, confirmPassword);
            showSuccess("Creando cuenta...");

            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                navegador.navigateTo(registerButton, "/View/MainView.fxml", "/View/css/mainview.css");
                Platform.runLater(() -> subject.notifyRegister(provisional));
            });

            pause.play();

        } catch (ValidationException | ExistingUserException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Retorna al usuario a la vista de inicio de sesión.
     * <p>
     * Muestra un mensaje informativo antes de realizar la navegación.
     * </p>
     */
    @FXML
    private void handleBackToLogin() {
        showInfo("Volviendo al inicio de sesión...");
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event
                -> navegador.navigateTo(registerButton, "/View/LoginView.fxml", "/View/css/loginview.css")
        );
        pause.play();
    }

    /**
     * Muestra un mensaje de error en la interfaz.
     *
     * @param message texto del mensaje a mostrar
     */
    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #ef4444;");
        showMessage();
    }

    /**
     * Muestra un mensaje de éxito en la interfaz.
     *
     * @param message texto del mensaje a mostrar
     */
    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #10b981;");
        showMessage();
    }

    /**
     * Muestra un mensaje informativo en la interfaz.
     *
     * @param message texto del mensaje a mostrar
     */
    private void showInfo(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #6366f1;");
        showMessage();
    }

    /**
     * Aplica una animación de aparición al mensaje mostrado.
     * <p>
     * Hace visible la etiqueta y utiliza una transición de opacidad para dar
     * feedback visual suave al usuario.
     * </p>
     */
    private void showMessage() {
        messageLabel.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
