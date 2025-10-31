/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.decorator;

import modelo.entity.Task;

/**
 * Componente base para decoradores de tareas
 */
public interface TaskComponent {
    String getId();
    String getTitulo();
    String getDescripcion();
    String getDisplayText();
    String getStyleClass();
    Task getTask();
}
