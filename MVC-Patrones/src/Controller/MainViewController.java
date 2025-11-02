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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Controlador principal de la vista de tareas (MainView.fxml).
 *
 * <p>
 * Gestiona la interacción del usuario con la lista de tareas, incluyendo la
 * creación, visualización, eliminación y conteo de tareas activas. También
 * administra el cierre de sesión del usuario autenticado y responde a los
 * eventos de autenticación mediante la implementación del patrón Observer.</p>
 *
 * <p>
 * Este controlador se apoya en las clases {@link TaskService} para la lógica de
 * negocio, {@link TaskUIBuilder} para la construcción visual de tareas, y
 * {@link NavigationManager} para la navegación entre vistas.</p>
 *
 * @author BENJAMIN
 */
public class MainViewController implements AuthObserver {

    /**
     * Campo de texto que permite al usuario ingresar datos de búsqueda o
     * acciones rápidas.
     */
    @FXML
    private TextField inputField;

    /**
     * Botón para crear una nueva tarea.
     */
    @FXML
    private Button addButton;

    /**
     * Contenedor principal que muestra todas las tareas activas del usuario.
     */
    @FXML
    private VBox taskListContainer;

    /**
     * Etiqueta que muestra el número de tareas pendientes.
     */
    @FXML
    private Label statsLabel;

    /**
     * Contenedor que se muestra cuando no existen tareas (estado vacío).
     */
    @FXML
    private VBox emptyStateBox;

    /**
     * Botón para cerrar la sesión actual del usuario.
     */
    @FXML
    private Button logoutButton;

    /**
     * Lista observable que mantiene sincronizadas las tareas con la UI.
     */
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    /**
     * Servicio encargado de la lógica de creación, obtención y eliminación de
     * tareas.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Repositorio de usuarios, utilizado para acceder a información
     * persistente.
     */
    private final UserRepository userRepository = UserRepository.getInstance();

    /**
     * Sujeto de autenticación que notifica eventos de login, logout y registro.
     */
    private final AuthSubject subject = AuthSubject.getInstance();

    /**
     * Constructor visual de elementos de tarea en la interfaz.
     */
    private final TaskUIBuilder taskUIBuilder = new TaskUIBuilder();

    /**
     * Manejador de eventos para las acciones sobre las tareas (completar,
     * eliminar, etc.).
     */
    private final TaskEventHandler taskEventHandler = new TaskEventHandler(this);

    /**
     * Gestor de navegación entre vistas dentro de la aplicación.
     */
    private final NavigationManager navegador = NavigationManager.getInstance();

    /**
     * Usuario autenticado actualmente en la sesión.
     */
    private User usuario;

    /**
     * Inicializa el controlador y configura los eventos de la vista.
     *
     * <p>
     * Se ejecuta automáticamente al cargar la vista FXML. Registra al
     * controlador como observador del sistema de autenticación, asigna los
     * eventos a botones e inicializa la interfaz.</p>
     */
    @FXML
    public void initialize() {
        subject.addObserver(this);
        addButton.setOnAction(e -> createTask());
        inputField.setOnAction(e -> createTask());

        if (logoutButton != null) {
            logoutButton.setOnAction(e -> handleLogout());
        }
    }

    /**
     * Abre el diálogo de creación de tarea y agrega una nueva tarea a la lista.
     *
     * <p>
     * Valida los datos ingresados por el usuario, crea la tarea mediante
     * {@link TaskService}, la agrega a la lista observable y actualiza las
     * estadísticas.</p>
     */
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
                showErrorAlert("Error al crear tarea", ex.getMessage());
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Maneja el cierre de sesión del usuario actual.
     *
     * <p>
     * Muestra un cuadro de confirmación antes de proceder. Si el usuario
     * confirma, se limpian las tareas cargadas, se actualiza la interfaz y se
     * redirige al login.</p>
     */
    @FXML
    private void handleLogout() {
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Cerrar Sesión");
        confirmAlert.setHeaderText("¿Estás seguro que deseas cerrar sesión?");
        confirmAlert.setContentText("Tus tareas se guardarán automáticamente.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tasks.clear();
            taskListContainer.getChildren().clear();
            showEmptyState();
            subject.notifyLogout(usuario);
            navegador.navigateTo(addButton, "/View/LoginView.fxml", "/View/css/loginview.css");
        }
    }

    /**
     * Agrega visualmente una tarea al contenedor principal de la interfaz.
     *
     * @param task la tarea a mostrar en la UI.
     */
    public void addTaskToUI(Task task) {
        HBox taskItem = taskUIBuilder.buildTaskItem(task, taskEventHandler);
        taskListContainer.getChildren().add(taskItem);
        hideEmptyState();
    }

    /**
     * Actualiza el contador de tareas pendientes mostrado en la interfaz.
     */
    public void updateStats() {
        long active = tasks.stream().filter(t -> !t.isCompletada()).count();
        statsLabel.setText(active + (active == 1 ? " tarea pendiente" : " tareas pendientes"));
    }

    /**
     * Muestra los detalles completos de una tarea en un cuadro de diálogo.
     *
     * @param task la tarea cuyos detalles se mostrarán.
     */
    public void showTaskDetails(Task task) {
        DialogDetails dialog = new DialogDetails(task);
        dialog.showAndWait();
    }

    /**
     * Elimina una tarea del sistema y actualiza la interfaz.
     *
     * @param task la tarea a eliminar.
     * @param taskItem el elemento visual asociado en la lista.
     */
    public void removeTask(Task task, HBox taskItem) {
        tasks.remove(task);
        taskListContainer.getChildren().remove(taskItem);
        taskService.eliminarTarea(task);
        updateStats();
        if (tasks.isEmpty()) {
            showEmptyState();
        }
    }

    /**
     * Oculta el estado vacío cuando existen tareas.
     */
    private void hideEmptyState() {
        emptyStateBox.setVisible(false);
        emptyStateBox.setManaged(false);
    }

    /**
     * Muestra el estado vacío cuando no hay tareas en la lista.
     */
    private void showEmptyState() {
        emptyStateBox.setVisible(true);
        emptyStateBox.setManaged(true);
    }

    /**
     * Muestra una alerta de error con título y mensaje personalizado.
     *
     * @param title título de la alerta.
     * @param message mensaje de error a mostrar.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Evento disparado cuando un usuario inicia sesión exitosamente.
     *
     * <p>
     * Carga las tareas asociadas al usuario autenticado, actualiza las
     * estadísticas y muestra la lista en la UI.</p>
     *
     * @param user el usuario que ha iniciado sesión.
     */
    @Override
    public void onLoginSuccess(User user) {
        usuario = user;
        tasks.setAll(taskService.obtenerTareasUsuario(user.getNombreUsuario()));
        tasks.forEach(this::addTaskToUI);
        updateStats();
        if (tasks.isEmpty()) {
            showEmptyState();
        }
    }

    /**
     * Evento disparado cuando un nuevo usuario se registra exitosamente.
     *
     * @param user el usuario recién registrado.
     */
    @Override
    public void onRegisterSuccess(User user) {
        usuario = user;
    }

    /**
     * Evento disparado cuando un usuario cierra sesión.
     *
     * @param user el usuario que ha cerrado sesión.
     */
    @Override
    public void onLogout(User user) {
        usuario = null;
        System.out.println("Usuario " + user.getNombreUsuario() + " ha cerrado sesión");
    }
}
