
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
 *
 * @author samue
 */
public class TaskEventHandler {

    private final MainViewController controller;
    private final TaskService taskService = new TaskService();

    public TaskEventHandler(MainViewController controller) {
        this.controller = controller;
    }

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

    public void handleDelete(Task task, HBox taskItem) {
        controller.removeTask(task, taskItem);
    }

    public void handleDetails(Task task) {
        controller.showTaskDetails(task);
    }
}
