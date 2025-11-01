/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import modelo.entity.Task;

/**
 * Implementación base de TaskComponent
 */
public class BaseTask implements TaskComponent {
    protected final Task task;

    public BaseTask(Task task) {
        this.task = task;
    }

    @Override
    public String getId() {
        return task.getId();
    }

    @Override
    public String getTitulo() {
        return task.getTitulo();
    }

    @Override
    public String getDescripcion() {
        return task.getDescripcion();
    }

    @Override
    public String getDisplayText() {
        return task.getTitulo();
    }

    @Override
    public String getStyleClass() {
        return "task-base";
    }

    @Override
    public Task getTask() {
        return task;
    }
}
