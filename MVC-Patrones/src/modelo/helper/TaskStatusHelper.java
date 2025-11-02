/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.helper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase de utilidad que proporciona métodos auxiliares para determinar
 * el estado visual y textual de las tareas según su fecha límite.
 * <p>
 * {@code TaskStatusHelper} centraliza la lógica de cálculo de días restantes,
 * asignación de etiquetas, iconos y estilos CSS basándose en la proximidad
 * de la fecha de vencimiento de una tarea.
 * </p>
 * 
 * 
 * <h3>Criterios de estado:</h3>
 * <ul>
 *   <li><b>Vencida:</b> Fecha límite anterior a hoy (días negativos)</li>
 *   <li><b>Hoy:</b> Fecha límite es el día actual (0 días)</li>
 *   <li><b>Por vencer:</b> Fecha límite dentro de 1-3 días</li>
 *   <li><b>Normal:</b> Fecha límite a más de 3 días o sin fecha</li>
 * </ul>
 *
 * @author samue
 */
public class TaskStatusHelper {
    
    /**
     * Calcula el número de días restantes desde hoy hasta la fecha límite especificada.
     * <p>
     * Si la fecha límite es {@code null}, retorna {@link Long#MAX_VALUE} indicando
     * que no hay límite temporal definido.
     * </p>
     * 
     * @param fechaLimite fecha límite de la tarea, puede ser {@code null}
     * @return número de días restantes; negativo si está vencida, 
     *         {@link Long#MAX_VALUE} si no tiene fecha límite
     */
    public static long calcularDiasRestantes(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
    }
    
    /**
     * Obtiene una etiqueta textual descriptiva del estado de la tarea según
     * los días restantes.
     * <p>
     * Las etiquetas posibles son:
     * <ul>
     *   <li>{@code "[VENCIDA]"} - cuando {@code diasRestantes < 0}</li>
     *   <li>{@code "[HOY]"} - cuando {@code diasRestantes == 0}</li>
     *   <li>{@code "(X días)"} - cuando {@code 1 <= diasRestantes <= 3}</li>
     *   <li>Cadena vacía - cuando {@code diasRestantes > 3}</li>
     * </ul>
     * </p>
     * 
     * @param diasRestantes número de días restantes para la fecha límite
     * @return etiqueta de estado correspondiente
     */
    public static String obtenerEtiquetaEstado(long diasRestantes) {
        if (diasRestantes < 0) {
            return "[VENCIDA]";
        }
        if (diasRestantes == 0) {
            return "[HOY]";
        }
        if (diasRestantes <= 3) {
            return "(" + diasRestantes + " días)";
        }
        return "";
    }
    
    /**
     * Obtiene un icono emoji que representa visualmente el estado de urgencia
     * de la tarea.
     * <p>
     * Los iconos asignados son:
     * <ul>
     *   <li>{@code "⚠️"} - Tarea vencida (días negativos)</li>
     *   <li>{@code "⏰"} - Tarea para hoy (0 días)</li>
     *   <li>{@code "⏳"} - Tarea próxima a vencer (1-3 días)</li>
     *   <li>Cadena vacía - Tarea sin urgencia (más de 3 días)</li>
     * </ul>
     * </p>
     * 
     * @param diasRestantes número de días restantes para la fecha límite
     * @return icono emoji correspondiente al estado de urgencia
     */
    public static String obtenerIcono(long diasRestantes) {
        if (diasRestantes < 0) {
            return "⚠️";
        }
        if (diasRestantes == 0) {
            return "⏰";
        }
        if (diasRestantes <= 3) {
            return "⏳";
        }
        return "";
    }
    
    /**
     * Determina la clase CSS que debe aplicarse a la tarea según su estado
     * de urgencia.
     * <p>
     * Las clases CSS retornadas son:
     * <ul>
     *   <li>{@code "task-overdue"} - para tareas vencidas</li>
     *   <li>{@code "task-expiring-soon"} - para tareas que vencen en 0-3 días</li>
     *   <li>Cadena vacía - para tareas sin urgencia especial</li>
     * </ul>
     * </p>
     * 
     * @param diasRestantes número de días restantes para la fecha límite
     * @return nombre de la clase CSS correspondiente o cadena vacía
     */
    public static String obtenerClaseCSS(long diasRestantes) {
        if (diasRestantes < 0) {
            return "task-overdue";
        }
        if (diasRestantes <= 3) {
            return "task-expiring-soon";
        }
        return "";
    }
}