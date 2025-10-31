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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import modelo.observer.AuthObserver;
import modelo.observer.AuthSubject;
import modelo.entity.Task;
import modelo.entity.User;
import modelo.exception.ExistingTaskException;
import modelo.exception.ValidationException;
import modelo.repository.UserRepository;
import modelo.service.DialogDetails;
import modelo.service.DialogTask;
import modelo.service.StyleTaskPresentation;
import modelo.service.TaskService;

/**
 * Controlador principal de la vista {@code MainView.fxml}.
 * 
 * <p>Gestiona las tareas del usuario autenticado: permite crearlas,
 * actualizarlas, eliminarlas y mostrarlas en la interfaz. Además,
 * reacciona a los eventos de autenticación mediante el patrón Observer.</p>
 * 
 * <p>Forma parte del patrón MVC, actuando como controlador entre la vista 
 * principal y los servicios del modelo.</p>
 */
public class MainViewController implements AuthObserver {

    /** Campo de entrada para el texto de la tarea. */
    @FXML private TextField inputField;

    /** Botón para agregar una nueva tarea. */
    @FXML private Button addButton;

    /** Contenedor visual donde se muestran las tareas. */
    @FXML private VBox taskListContainer;

    /** Etiqueta que muestra estadísticas (pendientes, completadas, etc.). */
    @FXML private Label statsLabel;

    /** Contenedor visible cuando no hay tareas. */
    @FXML private VBox emptyStateBox;

    /** Lista observable que mantiene las tareas mostradas en la vista. */
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    /** Clase auxiliar para manejar el estilo visual de las tareas. */
    private StyleTaskPresentation style = new StyleTaskPresentation();

    /** Sujeto que notifica los eventos de autenticación a los observadores. */
    private AuthSubject subject;

    /** Servicio de negocio encargado de manejar las operaciones sobre tareas. */
    private TaskService taskService;

    /** Repositorio de usuarios persistentes. */
    private UserRepository userRepositorio;

    /** Usuario actualmente autenticado. */
    private User usuario;

    /**
     * Inicializa el controlador y configura los eventos principales.
     * 
     * <p>Se ejecuta automáticamente cuando la vista {@code MainView.fxml} 
     * se carga. Registra el controlador como observador de eventos de autenticación
     * y asigna los manejadores para los botones y campos de entrada.</p>
     */
    @FXML
    public void initialize() {
        taskService = new TaskService();
        userRepositorio = UserRepository.getInstance();
        subject = AuthSubject.getInstance();
        subject.addObserver(this);

        addButton.setOnAction(e -> createTask());
        inputField.setOnAction(e -> createTask());
    }

    /**
     * Crea una nueva tarea mediante un diálogo interactivo.
     * 
     * <p>Cuando el usuario confirma la creación, la tarea se agrega tanto
     * al modelo (mediante {@link TaskService}) como a la interfaz visual.</p>
     */
    @FXML
    private void createTask() {
        DialogTask dialog = new DialogTask();
        dialog.showAndWait().ifPresent(taskDataUI -> {
            Task task = new Task(
                    taskDataUI.getTitulo(),
                    taskDataUI.getDescripcion(),
                    taskDataUI.getFechaLimite(),
                    taskDataUI.getPrioridad()
            );
            try {
                taskService.crearTarea(
                        task.getTitulo(),
                        task.getDescripcion(),
                        task.getFechaLimite(),
                        task.getPrioridad(),
                        usuario.getNombreUsuario()
                );
            } catch (ValidationException | ExistingTaskException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tasks.add(task);
            addTaskToUI(task);
            updateStats();
        });
    }

    /**
     * Agrega una tarea al contenedor visual de la interfaz.
     *
     * @param task tarea a mostrar en el panel de tareas
     */
    public void addTaskToUI(Task task) {
        HBox taskItem = new HBox(15);
        taskItem.setAlignment(Pos.CENTER_LEFT);
        taskItem.setPadding(new Insets(15));
        taskItem.getStyleClass().add("task-item");

        taskItem.setOnMouseClicked(e -> showTaskDetails(task));

        String baseColor = task.getPriorityBackgroundColor();
        String opacity = task.isCompletada() ? "0.3" : "1.0";

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("task-checkbox");
        checkBox.setSelected(task.isCompletada());
        checkBox.setOnAction(e -> {
            task.setCompletada(checkBox.isSelected());
            style.updateTaskStyle(taskItem, checkBox.isSelected(), task);
            updateStats();
            try {
                taskService.actualizarTarea(task);
            } catch (ValidationException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Label taskLabel = new Label(task.getTitulo());
        taskLabel.getStyleClass().add("task-label");
        HBox.setHgrow(taskLabel, Priority.ALWAYS);

        Button deleteBtn = new Button("✕");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> {
            tasks.remove(task);
            taskListContainer.getChildren().remove(taskItem);
            updateStats();
            updateUI();
            taskService.eliminarTarea(task);
        });

        taskItem.getChildren().addAll(checkBox, taskLabel, deleteBtn);
        taskItem.setStyle(
                "-fx-background-color: " + baseColor + "; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
                "-fx-opacity: " + opacity + "; " +
                "-fx-cursor: hand;"
        );

        if (emptyStateBox.isVisible()) {
            emptyStateBox.setVisible(false);
            emptyStateBox.setManaged(false);
        }

        taskListContainer.getChildren().add(taskItem);
    }

    /**
     * Actualiza la visibilidad del estado vacío según la lista de tareas.
     */
    private void updateUI() {
        if (tasks.isEmpty()) {
            emptyStateBox.setVisible(true);
            emptyStateBox.setManaged(true);
        }
    }

    /**
     * Muestra un cuadro de diálogo con los detalles de una tarea.
     *
     * @param task tarea cuyos detalles se van a mostrar
     */
    private void showTaskDetails(Task task) {
        DialogDetails dialog = new DialogDetails(task);
        dialog.showAndWait();
    }

    /**
     * Actualiza las estadísticas visibles de tareas pendientes y completadas.
     */
    private void updateStats() {
        long activeTasks = tasks.stream().filter(t -> !t.isCompletada()).count();
        String pendingText = activeTasks + (activeTasks == 1 ? " tarea pendiente" : " tareas pendientes");
        statsLabel.setText(pendingText);
    }

    /**
     * Método del patrón Observer. Se ejecuta cuando un usuario inicia sesión.
     * 
     * <p>Carga las tareas asociadas al usuario autenticado y actualiza la interfaz.</p>
     *
     * @param user usuario autenticado
     */
    @Override
    public void onLoginSuccess(User user) {
        if (taskService.obtenerTareasUsuario(user.getNombreUsuario()).isEmpty()) {
            updateUI();
        } else {
            tasks = FXCollections.observableArrayList(taskService.obtenerTareasUsuario(user.getNombreUsuario()));
            for (Task tarea : tasks) {
                addTaskToUI(tarea);
            }
            updateStats();
        }
        this.usuario = user;
    }

    /**
     * Método del patrón Observer. Se ejecuta cuando un usuario se registra correctamente.
     *
     * @param user nuevo usuario registrado
     */
    @Override
    public void onRegisterSuccess(User user) {
        userRepositorio.save(user);
        this.usuario = user;
    }

    /**
     * Método del patrón Observer. Se ejecuta cuando un usuario cierra sesión.
     *
     * @param user usuario que cierra sesión
     */
    @Override
    public void onLogout(User user) {
        throw new UnsupportedOperationException("Funcionalidad de cierre de sesión no implementada aún.");
    }
}
