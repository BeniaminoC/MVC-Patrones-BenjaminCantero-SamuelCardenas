/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador para tareas urgentes
 */
public class UrgentTaskDecorator extends TaskDecorator {

    public UrgentTaskDecorator(TaskComponent task) {
        super(task);
    }

    @Override
    public String getDisplayText() {
        return "🔥 " + wrappedTask.getDisplayText() + " [URGENTE]";
    }

    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-urgent";
    }
}