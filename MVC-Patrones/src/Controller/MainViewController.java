/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Controller.handler.TaskEventHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.entity.Task;
import modelo.entity.User;
import modelo.exception.ExistingTaskException;
import modelo.exception.ValidationException;
import modelo.observer.AuthObserver;
import modelo.observer.AuthSubject;
import modelo.repository.UserRepository;
import modelo.service.TaskService;
import View.dialogs.DialogDetails;
import View.dialogs.DialogTask;
import Controller.util.TaskUIBuilder;
import Controller.handler.TaskEventHandler;

/**
 * Controlador principal de la vista MainView.fxml.
 * Coordina la vista y la lógica de negocio de las tareas del usuario autenticado.
 */
public class MainViewController implements AuthObserver {

    @FXML private TextField inputField;
    @FXML private Button addButton;
    @FXML private VBox taskListContainer;
    @FXML private Label statsLabel;
    @FXML private VBox emptyStateBox;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final TaskService taskService = new TaskService();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final AuthSubject subject = AuthSubject.getInstance();
    private final TaskUIBuilder taskUIBuilder = new TaskUIBuilder();
    private final TaskEventHandler taskEventHandler = new TaskEventHandler(this);

    private User usuario;

    @FXML
    public void initialize() {
        subject.addObserver(this);
        addButton.setOnAction(e -> createTask());
        inputField.setOnAction(e -> createTask());
    }

    @FXML
    private void createTask() {
        DialogTask dialog = new DialogTask();
        dialog.showAndWait().ifPresent(taskData -> {
            try {
                Task newTask = taskService.crearTarea(
                    taskData.getTitulo(),
                    taskData.getDescripcion(),
                    taskData.getFechaLimite(),
                    taskData.getPrioridad(),
                    usuario.getNombreUsuario()
                );
                tasks.add(newTask);
                addTaskToUI(newTask);
                updateStats();
            } catch (ValidationException | ExistingTaskException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addTaskToUI(Task task) {
        HBox taskItem = taskUIBuilder.buildTaskItem(task, taskEventHandler);
        taskListContainer.getChildren().add(taskItem);
        hideEmptyState();
    }

    public void updateStats() {
        long active = tasks.stream().filter(t -> !t.isCompletada()).count();
        statsLabel.setText(active + (active == 1 ? " tarea pendiente" : " tareas pendientes"));
    }

    public void showTaskDetails(Task task) {
        DialogDetails dialog = new DialogDetails(task);
        dialog.showAndWait();
    }

    public void removeTask(Task task, HBox taskItem) {
        tasks.remove(task);
        taskListContainer.getChildren().remove(taskItem);
        taskService.eliminarTarea(task);
        updateStats();
        if (tasks.isEmpty()) showEmptyState();
    }

    private void hideEmptyState() {
        emptyStateBox.setVisible(false);
        emptyStateBox.setManaged(false);
    }

    private void showEmptyState() {
        emptyStateBox.setVisible(true);
        emptyStateBox.setManaged(true);
    }

    @Override
    public void onLoginSuccess(User user) {
        usuario = user;
        tasks.setAll(taskService.obtenerTareasUsuario(user.getNombreUsuario()));
        tasks.forEach(this::addTaskToUI);
        updateStats();
        if (tasks.isEmpty()) showEmptyState();
    }

    @Override
    public void onRegisterSuccess(User user) {
        userRepository.save(user);
        usuario = user;
    }

    @Override
    public void onLogout(User user) {
        throw new UnsupportedOperationException("Funcionalidad de cierre de sesión no implementada aún.");
    }
}
