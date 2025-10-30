/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Decorador para tareas próximas a vencer
 */
public class ExpiringTaskDecorator extends TaskDecorator {

    public ExpiringTaskDecorator(TaskComponent task) {
        super(task);
    }

    @Override
    public String getDisplayText() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        if (fechaLimite == null) {
            return wrappedTask.getDisplayText();
        }

        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
        
        if (diasRestantes < 0) {
            return "⚠️ " + wrappedTask.getDisplayText() + " [VENCIDA]";
        } else if (diasRestantes == 0) {
            return "⏰ " + wrappedTask.getDisplayText() + " [HOY]";
        } else if (diasRestantes <= 3) {
            return "⏳ " + wrappedTask.getDisplayText() + " (" + diasRestantes + " días)";
        }
        
        return wrappedTask.getDisplayText();
    }

    @Override
    public String getStyleClass() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        if (fechaLimite == null) {
            return wrappedTask.getStyleClass();
        }

        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
        
        if (diasRestantes < 0) {
            return wrappedTask.getStyleClass() + " task-overdue";
        } else if (diasRestantes <= 3) {
            return wrappedTask.getStyleClass() + " task-expiring-soon";
        }
        
        return wrappedTask.getStyleClass();
    }
}
