/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.util;

import javafx.scene.layout.HBox;
import modelo.entity.Task;

/**
 * Clase auxiliar para aplicar estilos visuales a tareas.
 * Centraliza la lógica de color y estilo según la prioridad.
 */
public class TaskStyleHelper {

    public static void applyStyle(HBox taskItem, Task task) {
        String baseColor = getPriorityBackgroundColor(task);
        taskItem.setStyle(
                "-fx-background-color: " + baseColor + "; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
        );
    }

    public static String getPriorityColor(Task task) {
        switch (task.getPrioridad()) {
            case "URGENTE": return "#dc2626";
            case "IMPORTANTE": return "#f59e0b";
            case "OPCIONAL": return "#10b981";
            default: return "#6b7280";
        }
    }

    public static String getPriorityBackgroundColor(Task task) {
        switch (task.getPrioridad()) {
            case "URGENTE": return "#fee2e2";
            case "IMPORTANTE": return "#fef3c7";
            case "OPCIONAL": return "#d1fae5";
            default: return "#f3f4f6";
        }
    }
}
