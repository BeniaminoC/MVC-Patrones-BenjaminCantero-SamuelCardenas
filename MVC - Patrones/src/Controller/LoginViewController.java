/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import modelo.entity.User;
import modelo.service.UserService;
import modelo.observer.AuthSubject;
import modelo.exception.AuthenticationException;

/**
 * Controlador de Login mejorado
 */
public class LoginViewController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label messageLabel;
    @FXML private Hyperlink registerLink;
    @FXML private ProgressIndicator progressIndicator;

    private final UserService userService;
    private final AuthSubject authSubject;
    private final NavigationManager navigator;

    public LoginViewController() {
        this.userService = new UserService();
        this.authSubject = AuthSubject.getInstance();
        this.navigator = NavigationManager.getInstance();
    }

    @FXML
    public void initialize() {
        messageLabel.setVisible(false);
        if (progressIndicator != null) {
            progressIndicator.setVisible(false);
        }
        
        loginButton.setOnAction(e -> handleLogin());
        passwordField.setOnAction(e -> handleLogin());
        registerLink.setOnAction(e -> handleRegister());
        
        // Focus en username
        javafx.application.Platform.runLater(() -> titleField.requestFocus());
    }

    private void setupContent() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Título
        VBox titleBox = new VBox(8);
        Label titleLabel = new Label("Título *");
        titleLabel.setStyle("-fx-font-weight: bold;");
        titleField = new TextField();
        titleField.setPromptText("Nombre de la tarea");
        titleBox.getChildren().addAll(titleLabel, titleField);
        
        // Descripción
        VBox descBox = new VBox(8);
        Label descLabel = new Label("Descripción");
        descLabel.setStyle("-fx-font-weight: bold;");
        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Detalles adicionales (opcional)");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setWrapText(true);
        descBox.getChildren().addAll(descLabel, descriptionArea);
        
        // Fecha y Prioridad
        HBox dateAndPriorityBox = new HBox(20);
        
        // Fecha
        VBox dateBox = new VBox(8);
        Label dateLabel = new Label("Fecha Límite");
        dateLabel.setStyle("-fx-font-weight: bold;");
        datePicker = new DatePicker();
        datePicker.setPromptText("dd/MM/yyyy");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }
            
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, formatter);
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }
        });
        
        HBox quickButtons = new HBox(5);
        Button todayBtn = createQuickButton("Hoy");
        Button tomorrowBtn = createQuickButton("Mañana");
        Button weekBtn = createQuickButton("1 Semana");
        
        todayBtn.setOnAction(e -> datePicker.setValue(LocalDate.now()));
        tomorrowBtn.setOnAction(e -> datePicker.setValue(LocalDate.now().plusDays(1)));
        weekBtn.setOnAction(e -> datePicker.setValue(LocalDate.now().plusWeeks(1)));
        
        quickButtons.getChildren().addAll(todayBtn, tomorrowBtn, weekBtn);
        dateBox.getChildren().addAll(dateLabel, datePicker, quickButtons);
        
        // Prioridad
        VBox priorityBox = new VBox(8);
        Label priorityLabel = new Label("Prioridad");
        priorityLabel.setStyle("-fx-font-weight: bold;");
        
        priorityGroup = new ToggleGroup();
        
        RadioButton urgentBtn = new RadioButton("🔥 Urgente");
        urgentBtn.setToggleGroup(priorityGroup);
        urgentBtn.setUserData("URGENTE");
        urgentBtn.setStyle("-fx-text-fill: #dc2626;");
        
        RadioButton importantBtn = new RadioButton("⚡ Importante");
        importantBtn.setToggleGroup(priorityGroup);
        importantBtn.setUserData("IMPORTANTE");
        importantBtn.setStyle("-fx-text-fill: #f59e0b;");
        
        RadioButton optionalBtn = new RadioButton("📌 Opcional");
        optionalBtn.setToggleGroup(priorityGroup);
        optionalBtn.setUserData("OPCIONAL");
        optionalBtn.setStyle("-fx-text-fill: #10b981;");
        optionalBtn.setSelected(true);
        
        priorityBox.getChildren().addAll(priorityLabel, urgentBtn, importantBtn, optionalBtn);
        
        dateAndPriorityBox.getChildren().addAll(dateBox, priorityBox);
        
        content.getChildren().addAll(titleBox, descBox, dateAndPriorityBox);
        getDialogPane().setContent(content);
    }

    private Button createQuickButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #e0e7ff; -fx-text-fill: #4f46e5; " +
                    "-fx-cursor: hand; -fx-background-radius: 5; -fx-font-size: 11px;");
        return btn;
    }

    private void setupButtons() {
        ButtonType createButton = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);
        
        Button createBtn = (Button) getDialogPane().lookupButton(createButton);
        createBtn.setDisable(true);
        
        // Habilitar botón solo si hay título
        titleField.textProperty().addListener((obs, old, newVal) -> {
            createBtn.setDisable(newVal.trim().isEmpty());
        });
    }

    private void setupResultConverter() {
        setResultConverter(button -> {
            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                String prioridad = (String) priorityGroup.getSelectedToggle().getUserData();
                return new MainViewController.TaskData(
                    titleField.getText().trim(),
                    descriptionArea.getText().trim(),
                    datePicker.getValue(),
                    prioridad
                );
            }
            return null;
        });
    }
}
