/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import java.time.LocalDate;
import modelo.helper.TaskStatusHelper;

/**
 * Decorador concreto que resalta visualmente las tareas próximas a vencer o
 * vencidas.
 * <p>
 * La clase <code>ExpiringTaskDecorator</code> extiende {@link TaskDecorator} y
 * modifica la presentación de una tarea en función del número de días restantes
 * hasta su fecha límite. Utiliza métodos de {@link TaskStatusHelper} para
 * determinar el ícono, la etiqueta descriptiva y la clase CSS adecuada según el
 * estado de vencimiento.
 * </p>
 *
 * <p>
 * Este decorador es parte del patrón <b>Decorator</b>, que permite añadir
 * comportamientos adicionales a objetos {@link TaskComponent} sin modificar su
 * estructura interna. En este caso, se utiliza para mostrar de forma visual el
 * estado temporal de una tarea (por vencer, vencida o vigente).
 * </p>
 *
 * @author Samuel
 */
public class ExpiringTaskDecorator extends TaskDecorator {

    /**
     * Crea una nueva instancia del decorador de tarea con vencimiento.
     *
     * @param task Componente {@link TaskComponent} que se desea decorar.
     */
    public ExpiringTaskDecorator(TaskComponent task) {
        super(task);
    }

    /**
     * Devuelve el texto de visualización de la tarea decorada, incluyendo:
     * <ul>
     * <li>Un ícono representativo del estado (por ejemplo, ⏰ o ⚠️).</li>
     * <li>El texto base de la tarea decorada.</li>
     * <li>Una etiqueta descriptiva del estado (por ejemplo, "Vence hoy" o "A
     * tiempo").</li>
     * </ul>
     * <p>
     * Los elementos visuales se determinan mediante los métodos de
     * {@link TaskStatusHelper}, de acuerdo con el número de días restantes
     * hasta la fecha límite.
     * </p>
     *
     * @return Texto de visualización enriquecido con información de
     * vencimiento.
     */
    @Override
    public String getDisplayText() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        long diasRestantes = TaskStatusHelper.calcularDiasRestantes(fechaLimite);

        String icono = TaskStatusHelper.obtenerIcono(diasRestantes);
        String etiqueta = TaskStatusHelper.obtenerEtiquetaEstado(diasRestantes);

        return String.format("%s %s %s", icono, wrappedTask.getDisplayText(), etiqueta).trim();
    }

    /**
     * Devuelve la clase de estilo CSS de la tarea, añadiendo una clase
     * adicional que indica el estado del vencimiento (por ejemplo,
     * <code>"task-expiring"</code> o <code>"task-overdue"</code>).
     * <p>
     * El cálculo de la clase CSS adicional se realiza mediante
     * {@link TaskStatusHelper#obtenerClaseCSS(long)}.
     * </p>
     *
     * @return Cadena con las clases CSS combinadas para la tarea con
     * vencimiento.
     */
    @Override
    public String getStyleClass() {
        LocalDate fechaLimite = getTask().getFechaLimite();
        long diasRestantes = TaskStatusHelper.calcularDiasRestantes(fechaLimite);
        String claseExtra = TaskStatusHelper.obtenerClaseCSS(diasRestantes);

        return String.join(" ", wrappedTask.getStyleClass(), claseExtra).trim();
    }
}
