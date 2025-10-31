/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.MainViewController;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author BENJAMIN
 */
public class StyleTaskPresentation {
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
    
    public void updateTaskStyle(HBox taskItem, boolean completed, Task task) {
        // Actualizar fondo con opacidad
        updateTaskBackground(taskItem, task, completed);
        
        // Actualizar estilo del texto
        Label label = (Label) taskItem.getChildren().get(1);
        
        if (completed) {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #9ca3af; -fx-strikethrough: true;");
        } else {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        }
    }
    
}
