/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 *
 * @author samue
 */
public abstract class TaskDecorator implements TaskComponent {
    protected final TaskComponent wrappedTask;

    public TaskDecorator(TaskComponent task) {
        this.wrappedTask = task;
    }

    @Override
    public String getId() {
        return wrappedTask.getId();
    }

    @Override
    public String getTitulo() {
        return wrappedTask.getTitulo();
    }

    @Override
    public String getDescripcion() {
        return wrappedTask.getDescripcion();
    }

    @Override
    public modelo.entity.Task getTask() {
        return wrappedTask.getTask();
    }
}