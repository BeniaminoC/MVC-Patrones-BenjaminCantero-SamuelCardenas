/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador para tareas completadas
 */
public class CompletedTaskDecorator extends TaskDecorator {

    public CompletedTaskDecorator(TaskComponent task) {
        super(task);
    }

    @Override
    public String getDisplayText() {
        return "✓ " + wrappedTask.getDisplayText();
    }

    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-completed";
    }
}