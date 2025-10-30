/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import Controller.MainViewController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author BENJAMIN
 */
public class DialogTask extends javafx.scene.control.Dialog<TaskDataUI>{
    private TextField titleField;
    private TextArea descriptionField;
    private DatePicker datePicker;
    private String selectedPriority = "OPCIONAL"; // valor por defecto

    public DialogTask(){
        setTitle("Nueva Tarea");
        setHeaderText("Crear nueva tarea");

        // Estilizar el header
        getDialogPane().setStyle("-fx-background-color: white;");
        javafx.application.Platform.runLater(() -> {
            Region header = (Region) getDialogPane().lookup(".header-panel");
            if (header != null) {
                header.setStyle("-fx-background-color: #4f46e5; -fx-text-fill: white;");
            }
            Label headerLabel = (Label) getDialogPane().lookup(".header-panel .label");
            if (headerLabel != null) {
                headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            }
        });

        // Contenido principal
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        // Campo Título
        VBox titleBox = new VBox(8);
        Label titleLabel = new Label("Título");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151;");
        titleField = new TextField();
        titleField.setPromptText("Ingresa el título de la tarea");
        titleField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(titleLabel, titleField);

        // Campo Descripción
        VBox descBox = new VBox(8);
        Label descLabel = new Label("Descripción");
        descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151;");
        descriptionField = new TextArea();
        descriptionField.setPromptText("Agrega una descripción (opcional)");
        descriptionField.setPrefRowCount(3);
        descriptionField.setWrapText(true);
        descriptionField.setStyle("-fx-font-size: 14px;");
        descBox.getChildren().addAll(descLabel, descriptionField);

        // Contenedor horizontal para Fecha y Prioridad
        HBox dateAndPriorityBox = new HBox(20);
        dateAndPriorityBox.setAlignment(Pos.TOP_LEFT);

        // Campo Fecha Límite
        VBox dateBox = new VBox(8);
        Label dateLabel = new Label("Fecha Límite");
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151;");
        datePicker = new DatePicker();
        datePicker.setPromptText("Selecciona una fecha (opcional)");
        datePicker.setStyle("-fx-pref-height: 40px;");
        datePicker.setPrefWidth(200);

        // Formato español
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

        // Botones rápidos de fecha
        HBox quickDateButtons = new HBox(10);
        Button todayBtn = new Button("Hoy");
        Button tomorrowBtn = new Button("Mañana");
        Button nextWeekBtn = new Button("Próxima semana");

        String quickBtnStyle = "-fx-background-color: #e0e7ff; -fx-text-fill: #4f46e5; -fx-cursor: hand; -fx-background-radius: 5;";
        todayBtn.setStyle(quickBtnStyle);
        tomorrowBtn.setStyle(quickBtnStyle);
        nextWeekBtn.setStyle(quickBtnStyle);

        todayBtn.setOnAction(e -> datePicker.setValue(LocalDate.now()));
        tomorrowBtn.setOnAction(e -> datePicker.setValue(LocalDate.now().plusDays(1)));
        nextWeekBtn.setOnAction(e -> datePicker.setValue(LocalDate.now().plusWeeks(1)));

        quickDateButtons.getChildren().addAll(todayBtn, tomorrowBtn, nextWeekBtn);
        dateBox.getChildren().addAll(dateLabel, datePicker, quickDateButtons);
        HBox.setHgrow(dateBox, Priority.ALWAYS);

        // Campo Prioridad
        VBox priorityBox = new VBox(8);
        Label priorityLabel = new Label("Prioridad");
        priorityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151;");

        VBox priorityButtons = new VBox(8);
        ToggleGroup priorityGroup = new ToggleGroup();

        RadioButton urgentBtn = new RadioButton("Urgente");
        urgentBtn.setToggleGroup(priorityGroup);
        urgentBtn.setUserData("URGENTE");
        urgentBtn.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");

        RadioButton importantBtn = new RadioButton("Importante");
        importantBtn.setToggleGroup(priorityGroup);
        importantBtn.setUserData("IMPORTANTE");
        importantBtn.setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");

        RadioButton optionalBtn = new RadioButton("Opcional");
        optionalBtn.setToggleGroup(priorityGroup);
        optionalBtn.setUserData("OPCIONAL");
        optionalBtn.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
        optionalBtn.setSelected(true);

        priorityGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                selectedPriority = (String) newToggle.getUserData();
            }
        });

        priorityButtons.getChildren().addAll(urgentBtn, importantBtn, optionalBtn);
        priorityBox.getChildren().addAll(priorityLabel, priorityButtons);

        dateAndPriorityBox.getChildren().addAll(dateBox, priorityBox);

        content.getChildren().addAll(titleBox, descBox, dateAndPriorityBox);
        getDialogPane().setContent(content);

        // Botones del diálogo
        ButtonType createButtonType = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        Node createButton = getDialogPane().lookupButton(createButtonType);
        createButton.setStyle("-fx-background-color: #4f46e5; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        createButton.setDisable(true);

        titleField.textProperty().addListener((obs, oldVal, newVal) -> {
            createButton.setDisable(newVal.trim().isEmpty());
        });

        // Convertir resultado
        setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new TaskDataUI(
                        titleField.getText().trim(),
                        descriptionField.getText().trim(),
                        datePicker.getValue(),
                        selectedPriority
                );
            }
            return null;
        });

        // Enfocar campo título
        javafx.application.Platform.runLater(() -> titleField.requestFocus());
    }

}
