/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador concreto que indica que una tarea ha sido completada.
 * <p>
 * La clase <code>CompletedTaskDecorator</code> extiende {@link TaskDecorator} y
 * modifica la representación visual de una tarea base para reflejar su estado
 * de finalización. Añade un prefijo con el símbolo <b>✓</b> al texto mostrado y
 * una clase de estilo adicional <code>"task-completed"</code> para su
 * representación en la interfaz de usuario.
 * </p>
 *
 * <p>
 * Este decorador es parte de la implementación del patrón <b>Decorator</b>,
 * permitiendo añadir dinámicamente comportamientos o estilos a instancias de
 * {@link TaskComponent} sin alterar sus clases originales.
 * </p>
 *
 * @author Samuel
 */
public class CompletedTaskDecorator extends TaskDecorator {

    /**
     * Crea una nueva instancia del decorador de tarea completada.
     *
     * @param task Componente {@link TaskComponent} que se desea decorar.
     */
    public CompletedTaskDecorator(TaskComponent task) {
        super(task);
    }

    /**
     * Devuelve el texto de visualización de la tarea decorada, añadiendo un
     * símbolo de verificación (<b>✓</b>) antes del texto original.
     *
     * @return Texto de visualización de la tarea completada.
     */
    @Override
    public String getDisplayText() {
        return "✓ " + wrappedTask.getDisplayText();
    }

    /**
     * Devuelve la clase de estilo CSS del componente, agregando la clase
     * <code>"task-completed"</code> al conjunto de estilos originales.
     *
     * @return Cadena con las clases CSS aplicables a la tarea completada.
     */
    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-completed";
    }
}
