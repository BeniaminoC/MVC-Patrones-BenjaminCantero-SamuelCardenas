/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import java.time.LocalDate;
import modelo.helper.TaskStatusHelper;

/**
 *
 * @author samue
 */
public class ExpiringTaskDecorator extends TaskDecorator {

    public ExpiringTaskDecorator(TaskComponent task) {
        super(task);
    }

    @Override
    public String getDisplayText() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        long diasRestantes = TaskStatusHelper.calcularDiasRestantes(fechaLimite);

        String icono = TaskStatusHelper.obtenerIcono(diasRestantes);
        String etiqueta = TaskStatusHelper.obtenerEtiquetaEstado(diasRestantes);

        return String.format("%s %s %s", icono, wrappedTask.getDisplayText(), etiqueta).trim();
    }

    @Override
    public String getStyleClass() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        long diasRestantes = TaskStatusHelper.calcularDiasRestantes(fechaLimite);
        String claseExtra = TaskStatusHelper.obtenerClaseCSS(diasRestantes);

        return String.join(" ", wrappedTask.getStyleClass(), claseExtra).trim();
    }
}
