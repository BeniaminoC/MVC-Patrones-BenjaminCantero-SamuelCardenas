/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.decorator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modelo.entity.Task;

/**
 * Factoría que crea componentes de tareas decorados según su estado,
 * prioridad y fecha límite.
 * <p>
 * {@code TaskDecoratorFactory} implementa el patrón de diseño <b>Factory</b>
 * junto con el patrón <b>Decorator</b>, encapsulando la lógica de decisión
 * sobre qué decoradores aplicar a una tarea basándose en sus características.
 * </p>
 * 
 * <p>
 * La factoría aplica decoradores de forma condicional y en un orden específico:
 * <ol>
 *   <li>Si la tarea está completada, aplica {@link CompletedTaskDecorator}</li>
 *   <li>Si NO está completada y tiene fecha límite:
 *     <ul>
 *       <li>Vencida o próxima a vencer (≤3 días): {@link ExpiringTaskDecorator}</li>
 *       <li>Con fecha límite mayor a 3 días: {@link DeadlineTaskDecorator}</li>
 *     </ul>
 *   </li>
 *   <li>Si tiene prioridad URGENTE (independiente de completitud): {@link UrgentTaskDecorator}</li>
 * </ol>
 * </p>
 * 
 *
 * @author samue
 * @see TaskComponent
 * @see TaskDecorator
 * @see Task
 */
public class TaskDecoratorFactory {
    
    /**
     * Crea un componente de tarea decorado según las características
     * de la tarea proporcionada.
     * <p>
     * Este método analiza el estado, prioridad y fecha límite de la tarea
     * para determinar qué decoradores aplicar. Los decoradores se apilan
     * en capas, permitiendo combinar múltiples comportamientos.
     * </p>
     * 
     * <h4>Lógica de decoración:</h4>
     * <ul>
     *   <li><b>Tarea completada:</b> Solo aplica {@link CompletedTaskDecorator},
     *       ignorando fecha límite y prioridad</li>
     *   <li><b>Tarea NO completada con fecha límite:</b>
     *     <ul>
     *       <li>Si vence hoy o en ≤3 días: {@link ExpiringTaskDecorator}</li>
     *       <li>Si vence en más de 3 días: {@link DeadlineTaskDecorator}</li>
     *     </ul>
     *   </li>
     *   <li><b>Prioridad URGENTE:</b> Aplica {@link UrgentTaskDecorator}
     *       adicional (solo si no está completada)</li>
     * </ul>
     * 
     *
     *
     * @param task tarea base a decorar, no debe ser {@code null}
     * @return componente de tarea decorado con los decoradores apropiados
     */
    public static TaskComponent createDecoratedTask(Task task) {
        TaskComponent component = new BaseTask(task);
        
        if (task.isCompletada()) {
            component = new CompletedTaskDecorator(component);
        } else {
            if (task.getFechaLimite() != null) {
                long diasRestantes = ChronoUnit.DAYS.between(
                    LocalDate.now(), task.getFechaLimite());
                
                if (diasRestantes <= 3) {
                    component = new ExpiringTaskDecorator(component);
                } else {
                    component = new DeadlineTaskDecorator(component);
                }
            }
            
            if ("URGENTE".equals(task.getPrioridad())) {
                component = new UrgentTaskDecorator(component);
            }
        }
        
        return component;
    }
}