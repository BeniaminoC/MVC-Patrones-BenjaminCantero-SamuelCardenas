/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 * Excepción que indica que una tarea con el mismo título ya existe en el
 * sistema.
 * <p>
 * Esta excepción se lanza generalmente durante la creación o registro de nuevas
 * tareas cuando el título proporcionado coincide con el de una tarea
 * previamente almacenada.
 * </p>
 *
 * <p>
 * Permite mantener la integridad de los datos evitando duplicados en el
 * repositorio o lista de tareas.
 * </p>
 *
 * @author samue
 */
public class ExistingTaskException extends ModelException {

    /**
     * Crea una nueva excepción indicando que la tarea especificada ya existe.
     *
     * @param titulo título de la tarea duplicada
     */
    public ExistingTaskException(String titulo) {
        super("La tarea '" + titulo + "' ya existe.");
    }
}
