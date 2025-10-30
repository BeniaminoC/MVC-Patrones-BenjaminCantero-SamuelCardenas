/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.DialogTask;
import Model.ObserverLog;
import Model.StyleTaskPresentation;
import Model.SubjectLog;
import Model.TaskUI;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewController implements ObserverLog{
    
    @FXML private TextField inputField;
    @FXML private Button addButton;
    @FXML private VBox taskListContainer;
    @FXML private Label statsLabel;
    @FXML private VBox emptyStateBox;
    private ObservableList<TaskUI> tasks = FXCollections.observableArrayList();
    private StyleTaskPresentation style = new StyleTaskPresentation();
    
    
    private SubjectLog subject;
    

    @Override
    public void loginSuccess(String id) {
        //codigo para cargar las tareas
    }

    @Override
    public void registerSuccess(String id) {
        //codigo para guardar la informacion basica en archivos
    }
    
    @FXML
    public void initialize() {
        // Configurar el botón de añadir
        addButton.setOnAction(e -> createTask());
        
        // Permitir añadir con Enter
        inputField.setOnAction(e -> createTask());
        
        // Mostrar estado vacío al inicio
        updateUI();
    }
    
    @FXML
    private void createTask() {
        // Abrir diálogo para crear tarea
        DialogTask dialog = new DialogTask();
        dialog.showAndWait().ifPresent(taskDataUI -> {
            TaskUI task = new TaskUI(
                taskDataUI.getTitle(),
                taskDataUI.getDescription(),
                taskDataUI.getDueDate(),
                taskDataUI.getPriority()
            );
            tasks.add(task);
            addTaskToUI(task);
            updateStats();
        });
    }   
    
    public void addTaskToUI(TaskUI task) {
        StyleTaskPresentation style = new StyleTaskPresentation();
        HBox taskItem = new HBox(15);
        taskItem.setAlignment(Pos.CENTER_LEFT);
        taskItem.setPadding(new Insets(15));
        taskItem.getStyleClass().add("task-item");
        String baseColor = task.getPriorityBackgroundColor();
        String opacity = "1.0";
        
        // Checkbox
        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("task-checkbox");
        checkBox.setOnAction(e -> {
            task.setCompleted(checkBox.isSelected());
            style.updateTaskStyle(taskItem, checkBox.isSelected(), task);
            updateStats();
        });
        
        // Task label
        Label taskLabel = new Label(task.getText());
        taskLabel.getStyleClass().add("task-label");
        HBox.setHgrow(taskLabel, Priority.ALWAYS);
        
        // Delete button
        Button deleteBtn = new Button("✕");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> {
            tasks.remove(task);
            taskListContainer.getChildren().remove(taskItem);
            updateStats();
            updateUI();
        });
        
        taskItem.getChildren().addAll(checkBox, taskLabel, deleteBtn);
        taskItem.setStyle(
        "-fx-background-color: " + baseColor + "; " +
        "-fx-background-radius: 10; " +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
        "-fx-opacity: " + opacity + "; " +
        "-fx-cursor: hand;"
    );
        
        // Agregar al contenedor
        if (emptyStateBox.isVisible()) {
            emptyStateBox.setVisible(false);
            emptyStateBox.setManaged(false);
        }
        taskListContainer.getChildren().add(taskItem);
    }
    
    private void updateUI() {
        if (tasks.isEmpty()) {
            emptyStateBox.setVisible(true);
            emptyStateBox.setManaged(true);
        }
    }
    
    private void updateStats() {
        long activeTasks = tasks.stream().filter(t -> !t.isCompleted()).count();
        long completedTasks = tasks.stream().filter(TaskUI::isCompleted).count();
        
        String pendingText = activeTasks + (activeTasks == 1 ? " tarea pendiente" : " tareas pendientes");
        statsLabel.setText(pendingText);
    }

    
}


