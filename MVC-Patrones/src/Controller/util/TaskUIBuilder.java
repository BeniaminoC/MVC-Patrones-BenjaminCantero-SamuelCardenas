/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import modelo.entity.Task;
import Controller.handler.TaskEventHandler;

/**
 * {@code TaskUIBuilder} es una clase utilitaria encargada de construir los
 * elementos visuales (UI) que representan las tareas en la interfaz principal.
 * <p>
 * Genera dinámicamente un contenedor {@link HBox} para cada tarea, integrando
 * los controles visuales asociados (checkbox, título, botón de eliminación)
 * y aplicando estilos según el estado y prioridad de la tarea.
 * </p>
 * 
 * <p><b>Responsabilidad principal:</b> encapsular la creación y estilo de los
 * componentes gráficos de una tarea, facilitando la separación entre lógica
 * de presentación y lógica de negocio.</p>
 * 
 * @author BENJAMIN
 */
public class TaskUIBuilder {

    /**
     * Construye el componente visual que representa una tarea individual.
     * <p>
     * El elemento incluye:
     * <ul>
     *   <li>Un {@link CheckBox} para marcar la tarea como completada.</li>
     *   <li>Una {@link Label} que muestra el título de la tarea.</li>
     *   <li>Un {@link Button} para eliminar la tarea.</li>
     * </ul>
     * Además, se aplica un estilo dinámico mediante
     * {@link TaskStyleHelper#applyStyle(HBox, Task)} y se asocian los eventos
     * correspondientes a las acciones del usuario (check, delete, details).
     * </p>
     * 
     * @param task la tarea que se representará gráficamente
     * @param handler el manejador de eventos asociado a las acciones de la tarea
     * @return un contenedor {@link HBox} configurado con la información y eventos de la tarea
     */
    public HBox buildTaskItem(Task task, TaskEventHandler handler) {
        HBox taskItem = new HBox(15);
        taskItem.setAlignment(Pos.CENTER_LEFT);
        taskItem.setPadding(new Insets(15));
        taskItem.getStyleClass().add("task-item");

        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(task.isCompletada());
        checkBox.setOnAction(e -> handler.handleCheckBox(task, taskItem, checkBox));

        Label title = new Label(task.getTitulo());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1f2937;");
        HBox.setHgrow(title, Priority.ALWAYS);

        Button deleteBtn = new Button("✕");
        deleteBtn.setStyle("-fx-background-color: transparent;");
        deleteBtn.setOnAction(e -> handler.handleDelete(task, taskItem));

        taskItem.getChildren().addAll(checkBox, title, deleteBtn);
        TaskStyleHelper.applyStyle(taskItem, task);

        taskItem.setOnMouseClicked(e -> handler.handleDetails(task));

        return taskItem;
    }
}
