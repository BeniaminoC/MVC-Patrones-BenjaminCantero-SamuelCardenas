/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.decorator;

import modelo.entity.Task;

/**
 * Interfaz que define el contrato para componentes visuales de tareas
 * en el sistema.
 * 
 * <p>
 * Los componentes que implementen esta interfaz deben proporcionar
 * información básica para su representación visual, incluyendo
 * identificación, contenido textual y estilo CSS.
 * </p>
 * 
 * <h3>Información proporcionada:</h3>
 * <ul>
 *   <li>Identificador único del componente</li>
 *   <li>Título y descripción de la tarea</li>
 *   <li>Texto formateado para visualización</li>
 *   <li>Clase CSS para estilización</li>
 *   <li>Acceso al objeto tarea subyacente</li>
 * </ul>
 * 
 * 
 * @author samue
 * @see Task
 */
public interface TaskComponent {
    
    /**
     * Obtiene el identificador único del componente de tarea.
     * <p>
     * Este identificador debe ser único dentro del sistema y generalmente
     * corresponde al ID de la tarea subyacente.
     * </p>
     *
     * @return identificador único del componente
     */
    String getId();
    
    /**
     * Obtiene el título de la tarea.
     * <p>
     * El título representa el nombre o encabezado principal de la tarea,
     * utilizado para su identificación rápida en la interfaz.
     * </p>
     *
     * @return título de la tarea
     */
    String getTitulo();
    
    /**
     * Obtiene la descripción detallada de la tarea.
     * <p>
     * La descripción proporciona información adicional sobre el contenido,
     * objetivos o instrucciones relacionadas con la tarea.
     * </p>
     *
     * @return descripción de la tarea
     */
    String getDescripcion();
    
    /**
     * Obtiene el texto formateado para mostrar en la interfaz de usuario.
     * <p>
     * Este método puede combinar varios campos de la tarea (título, estado,
     * fecha límite, etc.) en un formato legible y presentable para el usuario.
     * </p>
     * 
     * 
     *
     * @return texto formateado para visualización
     */
    String getDisplayText();
    
    /**
     * Obtiene la clase CSS que debe aplicarse al componente para su estilización.
     * <p>
     * La clase CSS determina la apariencia visual del componente según su estado,
     * prioridad o urgencia.
     * </p>
     *
     * @return nombre de la clase CSS o cadena vacía si no requiere estilo especial
     */
    String getStyleClass();
    
    /**
     * Obtiene el objeto {@link Task} subyacente que representa este componente.
     * <p>
     * Este método proporciona acceso directo a la entidad de tarea completa,
     * permitiendo operaciones más complejas o acceso a propiedades no expuestas
     * directamente por la interfaz.
     * </p>
     *
     * @return objeto tarea asociado al componente
     */
    Task getTask();
}
