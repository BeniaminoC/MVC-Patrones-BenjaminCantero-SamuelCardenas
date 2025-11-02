/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Clase abstracta base que implementa el patrón de diseño <b>Decorator</b>
 * para componentes de tareas.
 * <p>
 * {@code TaskDecorator} permite agregar funcionalidades o modificar el
 * comportamiento de un {@link TaskComponent} existente de forma dinámica,
 * sin alterar su estructura original. Esta clase envuelve un componente
 * de tarea y delega las llamadas a los métodos básicos.
 * </p>
 * 
 * <p>
 * Las subclases pueden sobrescribir métodos específicos para añadir
 * comportamiento adicional, como:
 * <ul>
 *   <li>Agregar indicadores de estado visual</li>
 *   <li>Aplicar estilos CSS dinámicos</li>
 *   <li>Formatear el texto de visualización</li>
 *   <li>Añadir iconos o etiquetas de urgencia</li>
 * </ul>
 * </p>
 * 
 * 
 *
 * @author samue
 * @see TaskComponent
 * @see Task
 */
public abstract class TaskDecorator implements TaskComponent {
    
    /**
     * Componente de tarea envuelto que será decorado.
     * <p>
     * Este atributo mantiene la referencia al componente original,
     * permitiendo delegar las operaciones y agregar funcionalidad
     * adicional en las subclases.
     * </p>
     */
    protected final TaskComponent wrappedTask;
    
    /**
     * Constructor que inicializa el decorador con un componente de tarea.
     * <p>
     * El componente proporcionado será envuelto y sus métodos serán
     * delegados por defecto, permitiendo que las subclases sobrescriban
     * selectivamente el comportamiento deseado.
     * </p>
     *
     * @param task componente de tarea a decorar, no debe ser {@code null}
     */
    public TaskDecorator(TaskComponent task) {
        this.wrappedTask = task;
    }
    
    /**
     * <p>
     * Implementación por defecto que delega la llamada al componente envuelto.
     * </p>
     */
    @Override
    public String getId() {
        return wrappedTask.getId();
    }
    
    /**
     * <p>
     * Implementación por defecto que delega la llamada al componente envuelto.
     * </p>
     */
    @Override
    public String getTitulo() {
        return wrappedTask.getTitulo();
    }
    
    /**
     * <p>
     * Implementación por defecto que delega la llamada al componente envuelto.
     * </p>
     */
    @Override
    public String getDescripcion() {
        return wrappedTask.getDescripcion();
    }
    
    /**
     * <p>
     * Implementación por defecto que delega la llamada al componente envuelto.
     * </p>
     */
    @Override
    public modelo.entity.Task getTask() {
        return wrappedTask.getTask();
    }
}