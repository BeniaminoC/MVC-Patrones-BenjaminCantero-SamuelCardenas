/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import modelo.entity.Task;

/**
 * Implementación base del componente {@link TaskComponent} dentro del patrón
 * <b>Decorator</b>.
 * <p>
 * La clase <code>BaseTask</code> actúa como un contenedor para objetos del tipo
 * {@link Task}, proporcionando una implementación base de los métodos definidos
 * en {@link TaskComponent}. Esta clase puede ser extendida por decoradores que
 * añadan funcionalidades adicionales sin modificar la estructura de la clase
 * original.
 * </p>
 *
 * <p>
 * Sirve como punto de partida para la extensión del comportamiento de las
 * tareas, permitiendo aplicar el principio de <i>abierto/cerrado</i> de la
 * programación orientada a objetos.
 * </p>
 *
 * @author Samuel
 */
public class BaseTask implements TaskComponent {

    /**
     * Referencia inmutable a la tarea base que se está decorando.
     */
    protected final Task task;

    /**
     * Crea una nueva instancia de <code>BaseTask</code> que envuelve una tarea
     * existente.
     *
     * @param task Objeto {@link Task} que se va a decorar o encapsular.
     */
    public BaseTask(Task task) {
        this.task = task;
    }

    /**
     * Devuelve el identificador único de la tarea.
     *
     * @return Identificador de la tarea.
     */
    @Override
    public String getId() {
        return task.getId();
    }

    /**
     * Obtiene el título de la tarea.
     *
     * @return Título de la tarea.
     */
    @Override
    public String getTitulo() {
        return task.getTitulo();
    }

    /**
     * Devuelve la descripción de la tarea.
     *
     * @return Descripción textual de la tarea.
     */
    @Override
    public String getDescripcion() {
        return task.getDescripcion();
    }

    /**
     * Obtiene el texto que se mostrará en la interfaz gráfica para representar
     * la tarea.
     * <p>
     * Por defecto, devuelve el mismo valor que {@link #getTitulo()}.
     * </p>
     *
     * @return Texto de visualización de la tarea.
     */
    @Override
    public String getDisplayText() {
        return task.getTitulo();
    }

    /**
     * Devuelve la clase de estilo CSS asociada a la tarea base.
     * <p>
     * Este valor puede ser modificado por decoradores para alterar la
     * apariencia visual en la interfaz de usuario.
     * </p>
     *
     * @return Nombre de la clase CSS asociada, por defecto
     * <code>"task-base"</code>.
     */
    @Override
    public String getStyleClass() {
        return "task-base";
    }

    /**
     * Devuelve el objeto {@link Task} encapsulado por esta clase base.
     *
     * @return Objeto {@link Task} original.
     */
    @Override
    public Task getTask() {
        return task;
    }
}
