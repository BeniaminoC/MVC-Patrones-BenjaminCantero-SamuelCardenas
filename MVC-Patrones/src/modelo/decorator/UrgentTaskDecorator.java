/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

/**
 * Decorador que añade indicadores visuales de urgencia a un componente de tarea.
 * <p>
 * {@code UrgentTaskDecorator} extiende {@link TaskDecorator} para agregar
 * marcadores visuales que resaltan tareas con prioridad urgente, incluyendo
 * un icono de fuego (🔥), una etiqueta de texto "[URGENTE]" y una clase CSS
 * específica para estilización.
 * </p>
 * 
 * <p>
 * Este decorador es aplicado por {@link TaskDecoratorFactory} cuando una tarea
 * tiene la prioridad establecida como "URGENTE" y no está completada.
 * </p>
 * 
 * <h3>Modificaciones aplicadas:</h3>
 * <ul>
 *   <li><b>Texto de visualización:</b> Agrega icono 🔥 al inicio y etiqueta [URGENTE] al final</li>
 *   <li><b>Clase CSS:</b> Añade la clase {@code "task-urgent"} para estilización especial</li>
 * </ul>
 * 
 * <h3>Ejemplo de transformación:</h3>
 * <pre>{@code
 * // Texto original
 * "Completar informe mensual"
 * 
 * // Después del decorador
 * "🔥 Completar informe mensual [URGENTE]"
 * 
 * // Clase CSS original
 * "task-expiring-soon"
 * 
 * // Después del decorador
 * "task-expiring-soon task-urgent"
 * }</pre>
 * 
 
 *
 * @author samue
 * @see TaskDecorator
 * @see TaskDecoratorFactory
 * @see TaskComponent
 */
public class UrgentTaskDecorator extends TaskDecorator {
    
    /**
     * Constructor que inicializa el decorador de urgencia con un componente de tarea.
     *
     * @param task componente de tarea a decorar con indicadores de urgencia,
     *             no debe ser {@code null}
     */
    public UrgentTaskDecorator(TaskComponent task) {
        super(task);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Sobrescribe el método para agregar un icono de fuego (🔥) al inicio
     * y la etiqueta "[URGENTE]" al final del texto de visualización original.
     * </p>
     * 
     * @return texto decorado con formato: "🔥 [texto original] [URGENTE]"
     */
    @Override
    public String getDisplayText() {
        return "🔥 " + wrappedTask.getDisplayText() + " [URGENTE]";
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Sobrescribe el método para añadir la clase CSS {@code "task-urgent"}
     * a las clases existentes del componente envuelto. Si el componente
     * envuelto ya tiene clases CSS, se concatenan con un espacio.
     * </p>
     * 
     * @return clases CSS originales más {@code "task-urgent"}
     */
    @Override
    public String getStyleClass() {
        return wrappedTask.getStyleClass() + " task-urgent";
    }
}