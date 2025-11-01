/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador para tareas con fecha límite
 */
public class DeadlineTaskDecorator extends TaskDecorator {

    public DeadlineTaskDecorator(TaskComponent task) {
        super(task);
    }

    @Override
    public String getDisplayText() {
        String fechaStr = getTask().getFechaLimiteFormatted();
        if (fechaStr.isEmpty()) {
            return wrappedTask.getDisplayText();
        }
        return wrappedTask.getDisplayText() + " 📅 " + fechaStr;
    }

    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-with-deadline";
    }
}