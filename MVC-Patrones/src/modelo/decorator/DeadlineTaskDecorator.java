/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador concreto que añade información sobre la fecha límite de una tarea.
 * <p>
 * La clase <code>DeadlineTaskDecorator</code> extiende {@link TaskDecorator} y
 * modifica la visualización de una tarea para incluir su fecha de vencimiento,
 * si está disponible. Además, agrega una clase de estilo CSS específica para
 * resaltar las tareas con fecha límite.
 * </p>
 *
 * <p>
 * Este decorador es parte de la implementación del patrón <b>Decorator</b>,
 * permitiendo añadir dinámicamente funcionalidades visuales o de presentación a
 * las tareas sin alterar su estructura original.
 * </p>
 *
 * <p>
 * Si la tarea no tiene una fecha límite definida, se mantiene el texto original
 * proporcionado por el componente decorado.
 * </p>
 *
 * @author Samuel
 */
public class DeadlineTaskDecorator extends TaskDecorator {

    /**
     * Crea una nueva instancia del decorador de tarea con fecha límite.
     *
     * @param task Componente {@link TaskComponent} que se desea decorar.
     */
    public DeadlineTaskDecorator(TaskComponent task) {
        super(task);
    }

    /**
     * Devuelve el texto de visualización de la tarea, añadiendo el ícono de
     * calendario (<b>📅</b>) seguido de la fecha límite formateada, en caso de
     * estar disponible.
     * <p>
     * Si la tarea no posee una fecha límite, se devuelve el texto original sin
     * modificaciones.
     * </p>
     *
     * @return Texto de visualización de la tarea, incluyendo la fecha límite si
     * existe.
     */
    @Override
    public String getDisplayText() {
        String fechaStr = getTask().getFechaLimiteFormatted();
        if (fechaStr.isEmpty()) {
            return wrappedTask.getDisplayText();
        }
        return wrappedTask.getDisplayText() + " 📅 " + fechaStr;
    }

    /**
     * Devuelve la clase de estilo CSS del componente, agregando la clase
     * <code>"task-with-deadline"</code> al conjunto de estilos originales.
     *
     * @return Cadena con las clases CSS aplicables a la tarea con fecha límite.
     */
    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-with-deadline";
    }
}
