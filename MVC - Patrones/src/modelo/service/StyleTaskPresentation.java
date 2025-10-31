/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.service;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import modelo.entity.Task;

/**
 * Clase encargada de aplicar estilos visuales dinámicos a los elementos de tareas
 * en la interfaz. Controla los colores, la opacidad y los efectos según el estado
 * (completada o pendiente) y la prioridad del objeto {@link Task}.
 * 
 * Esta clase es utilizada por los controladores para actualizar la apariencia
 * de las tareas en la lista visual sin alterar su lógica subyacente.
 * 
 * @author BENJAMIN
 */
public class StyleTaskPresentation {

    /**
     * Actualiza el fondo del contenedor visual de una tarea según su estado
     * y prioridad.
     * 
     * @param taskItem elemento {@link HBox} que representa la tarea en la interfaz
     * @param task objeto {@link Task} asociado al contenedor
     * @param completed indica si la tarea está completada
     */
    public void updateTaskBackground(HBox taskItem, Task task, boolean completed) {
        String baseColor = task.getPriorityBackgroundColor();
        String opacity = completed ? "0.3" : "1.0";
        
        taskItem.setStyle(
            "-fx-background-color: " + baseColor + "; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
            "-fx-opacity: " + opacity + "; " +
            "-fx-cursor: hand;"
        );
    }

    /**
     * Actualiza el estilo completo del elemento visual de la tarea, incluyendo
     * el fondo y el texto, para reflejar si está completada o no.
     * 
     * @param taskItem elemento {@link HBox} que contiene la tarea
     * @param completed indica si la tarea está completada
     * @param task objeto {@link Task} con la información de la tarea
     */
    public void updateTaskStyle(HBox taskItem, boolean completed, Task task) {
        updateTaskBackground(taskItem, task, completed);
        
        Label label = (Label) taskItem.getChildren().get(1);
        
        if (completed) {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #9ca3af; -fx-strikethrough: true;");
        } else {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        }
    }
}

