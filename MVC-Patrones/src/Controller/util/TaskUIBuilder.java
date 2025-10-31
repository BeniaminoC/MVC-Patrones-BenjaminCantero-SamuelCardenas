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
 * Construye la representación visual de una tarea.
 */
public class TaskUIBuilder {

    public HBox buildTaskItem(Task task, TaskEventHandler handler) {
        HBox taskItem = new HBox(15);
        taskItem.setAlignment(Pos.CENTER_LEFT);
        taskItem.setPadding(new Insets(15));
        taskItem.getStyleClass().add("task-item");

        CheckBox checkBox = new CheckBox();        checkBox.setSelected(task.isCompletada());
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

