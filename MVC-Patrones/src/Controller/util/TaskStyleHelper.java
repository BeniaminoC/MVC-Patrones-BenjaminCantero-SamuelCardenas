/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.util;

import javafx.scene.layout.HBox;
import modelo.entity.Task;

/**
 * {@code TaskStyleHelper} proporciona utilidades para aplicar estilos visuales
 * coherentes a los elementos de tarea ({@link HBox}) dentro de la interfaz.
 * <p>
 * Su propósito es mantener la lógica de diseño separada del controlador,
 * definiendo reglas visuales según el nivel de prioridad o el estado de
 * completado de cada tarea.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Asignar colores de fondo y opacidad según la prioridad de la tarea.</li>
 *   <li>Distinguir visualmente tareas completadas mediante efectos y transparencia.</li>
 *   <li>Centralizar las reglas de estilo para mantener consistencia en la UI.</li>
 * </ul>
 *
 * <p>Se utiliza principalmente desde {@link Controller.handler.TaskEventHandler}
 * y {@link Controller.util.TaskUIBuilder}.</p>
 *
 * @author BENJAMIN 
 */
public class TaskStyleHelper {

    /**
     * Aplica el estilo visual correspondiente a un elemento de tarea.
     * <p>
     * Determina el color base según la prioridad, ajusta la opacidad si la tarea
     * está completada y añade efectos visuales como sombras suaves.
     * </p>
     *
     * @param taskItem el contenedor visual de la tarea en la interfaz
     * @param task la tarea cuyos atributos determinan el estilo
     */
    public static void applyStyle(HBox taskItem, Task task) {
        String baseColor = getPriorityBackgroundColor(task);
        String opacity = task.isCompletada() ? "0.3" : "1.0";
        taskItem.setStyle(
                "-fx-background-color: " + baseColor + "; "
                + "-fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); "
                + "-fx-opacity: " + opacity + "; "
                + "-fx-cursor: hand;"
        );
    }

    /**
     * Retorna el color principal asociado a la prioridad de la tarea.
     * <p>
     * Este color puede emplearse para etiquetas, bordes o íconos.
     * </p>
     *
     * @param task la tarea de la cual se obtiene la prioridad
     * @return color hexadecimal representativo de la prioridad
     */
    public static String getPriorityColor(Task task) {
        switch (task.getPrioridad()) {
            case "URGENTE":
                return "#dc2626"; // rojo
            case "IMPORTANTE":
                return "#f59e0b"; // naranja
            case "OPCIONAL":
                return "#10b981"; // verde
            default:
                return "#6b7280"; // gris neutro
        }
    }

    /**
     * Retorna el color de fondo correspondiente al nivel de prioridad de la tarea.
     * <p>
     * Se utiliza para el área principal de cada tarjeta de tarea.
     * </p>
     *
     * @param task la tarea a evaluar
     * @return color hexadecimal del fondo acorde a la prioridad
     */
    public static String getPriorityBackgroundColor(Task task) {
        switch (task.getPrioridad()) {
            case "URGENTE":
                return "#fee2e2"; // fondo rosado claro
            case "IMPORTANTE":
                return "#fef3c7"; // fondo amarillo claro
            case "OPCIONAL":
                return "#d1fae5"; // fondo verde claro
            default:
                return "#f3f4f6"; // fondo gris claro por defecto
        }
    }
}
