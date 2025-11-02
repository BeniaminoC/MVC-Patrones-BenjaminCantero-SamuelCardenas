
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.handler;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import modelo.entity.Task;
import modelo.exception.ValidationException;
import modelo.service.TaskService;
import Controller.util.TaskStyleHelper;
import Controller.MainViewController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code TaskEventHandler} gestiona los eventos de interacción del usuario
 * sobre las tareas mostradas en la interfaz principal.
 * <p>
 * Centraliza la respuesta a acciones como marcar tareas completadas,
 * eliminarlas o visualizar sus detalles, comunicándose directamente con el
 * {@link MainViewController} y los servicios de negocio.
 * </p>
 *
 * <p>
 * <b>Responsabilidades:</b></p>
 * <ul>
 * <li>Actualizar el estado de las tareas al marcar o desmarcar el
 * checkbox.</li>
 * <li>Notificar cambios visuales en la interfaz mediante
 * {@link TaskStyleHelper}.</li>
 * <li>Eliminar tareas y refrescar las estadísticas globales.</li>
 * <li>Invocar la vista de detalles de una tarea seleccionada.</li>
 * </ul>
 *
 * <p>
 * Este manejador se asocia a cada elemento de tarea construido dinámicamente en
 * la interfaz mediante {@link Controller.util.TaskUIBuilder}.</p>
 *
 * @author samue
 */
public class TaskEventHandler {

    /**
     * Controlador principal que administra la vista de tareas.
     */
    private final MainViewController controller;

    /**
     * Servicio responsable de la persistencia y validación de tareas.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Crea un nuevo manejador de eventos para tareas.
     *
     * @param controller instancia del controlador principal asociada a la vista
     */
    public TaskEventHandler(MainViewController controller) {
        this.controller = controller;
    }

    /**
     * Maneja el evento de selección del {@link CheckBox} asociado a una tarea.
     * <p>
     * Actualiza el estado de completado de la tarea, ajusta el estilo visual,
     * actualiza las estadísticas y persiste los cambios.
     * </p>
     *
     * @param task la tarea afectada
     * @param taskItem el contenedor visual correspondiente a la tarea
     * @param checkBox el componente de selección asociado
     */
    public void handleCheckBox(Task task, HBox taskItem, CheckBox checkBox) {
        task.setCompletada(checkBox.isSelected());
        TaskStyleHelper.applyStyle(taskItem, task);
        controller.updateStats();
        try {
            taskService.actualizarTarea(task);
        } catch (ValidationException ex) {
            Logger.getLogger(TaskEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maneja el evento de eliminación de una tarea.
     * <p>
     * Delegado al controlador principal para remover la tarea tanto del modelo
     * como de la interfaz visual.
     * </p>
     *
     * @param task la tarea que se eliminará
     * @param taskItem el componente visual que representa a la tarea
     */
    public void handleDelete(Task task, HBox taskItem) {
        controller.removeTask(task, taskItem);
    }

    /**
     * Maneja la solicitud de visualización de los detalles de una tarea.
     *
     * @param task la tarea cuyos detalles se desean visualizar
     */
    public void handleDetails(Task task) {
        controller.showTaskDetails(task);
    }
}
