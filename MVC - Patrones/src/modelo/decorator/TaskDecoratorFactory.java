/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modelo.entity.Task;

/**
 * Factory para crear tareas decoradas según sus propiedades
 */
public class TaskDecoratorFactory {

    public static TaskComponent createDecoratedTask(Task task) {
        TaskComponent component = new BaseTask(task);

        // Decorar si está completada
        if (task.isCompletada()) {
            component = new CompletedTaskDecorator(component);
        } else {
            // Solo decorar con fecha límite si NO está completada
            if (task.getFechaLimite() != null) {
                long diasRestantes = ChronoUnit.DAYS.between(
                    LocalDate.now(), task.getFechaLimite());
                
                // Decorar si está vencida o próxima a vencer
                if (diasRestantes <= 3) {
                    component = new ExpiringTaskDecorator(component);
                } else {
                    component = new DeadlineTaskDecorator(component);
                }
            }

            // Decorar si es urgente
            if ("URGENTE".equals(task.getPrioridad())) {
                component = new UrgentTaskDecorator(component);
            }
        }

        return component;
    }
}