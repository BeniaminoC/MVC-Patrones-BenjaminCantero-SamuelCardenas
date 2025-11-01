/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.dialogs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import modelo.entity.Task;
import Controller.util.TaskStyleHelper;
import Controller.util.TaskUrgencyHelper;
import Controller.util.TaskUrgencyHelper.UrgencyInfo;

/**
 *
 * @author BENJAMIN
 */
public class DialogDetails extends javafx.scene.control.Dialog<Task> {

    private Task task;

    public DialogDetails(Task task) {
        this.task = task;
        initializeDialog();
    }

    private void initializeDialog() {
        setTitle("Detalles de la Tarea");
        setHeaderText(task.getTitulo());

        getDialogPane().setStyle("-fx-background-color: white;");

        javafx.application.Platform.runLater(() -> {
            Region header = (Region) getDialogPane().lookup(".header-panel");
            if (header != null) {
                header.setStyle("-fx-background-color: " + TaskStyleHelper.getPriorityColor(task) + "; -fx-text-fill: white;");
            }
            Label headerLabel = (Label) getDialogPane().lookup(".header-panel .label");
            if (headerLabel != null) {
                headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            }
        });

        VBox content = createContent();
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        javafx.application.Platform.runLater(() -> {
            Button closeButton = (Button) getDialogPane().lookupButton(ButtonType.CLOSE);
            if (closeButton != null) {
                closeButton.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-cursor: hand;");
            }
        });
    }

    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: white;");
        content.setPrefWidth(450);

        content.getChildren().add(createStatusSection());
        content.getChildren().add(createPrioritySection());

        if (task.getDescripcion() != null && !task.getDescripcion().isEmpty()) {
            content.getChildren().add(createDescriptionSection());
        }

        if (task.getFechaLimite() != null) {
            content.getChildren().add(createDueDateSection());
        }

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #e5e7eb;");
        content.getChildren().add(separator);

        content.getChildren().add(createAdditionalInfo());

        return content;
    }

    private VBox createStatusSection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Estado");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        HBox statusBox = new HBox(10);
        statusBox.setAlignment(Pos.CENTER_LEFT);

        String statusIcon = task.isCompletada() ? "✓" : "○";
        String statusText = task.isCompletada() ? "Completada" : "Pendiente";
        String statusColor = task.isCompletada() ? "#10b981" : "#6b7280";

        Label statusLabel = new Label(statusIcon + " " + statusText);
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + statusColor + "; -fx-font-weight: bold;");

        statusBox.getChildren().add(statusLabel);
        section.getChildren().addAll(titleLabel, statusBox);
        return section;
    }

    private VBox createPrioritySection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Prioridad");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        Label priorityBadge = new Label(task.getPrioridad());
        priorityBadge.setStyle(
                "-fx-background-color: " + TaskStyleHelper.getPriorityColor(task) + "; "
                + "-fx-text-fill: white; "
                + "-fx-padding: 8 16; "
                + "-fx-background-radius: 20; "
                + "-fx-font-size: 14px; "
                + "-fx-font-weight: bold;"
        );

        section.getChildren().addAll(titleLabel, priorityBadge);
        return section;
    }

    private VBox createDescriptionSection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Descripción");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        Label descContent = new Label(task.getDescripcion());
        descContent.setWrapText(true);
        descContent.setStyle(
                "-fx-font-size: 14px; "
                + "-fx-text-fill: #374151; "
                + "-fx-padding: 12; "
                + "-fx-background-color: #f9fafb; "
                + "-fx-background-radius: 8;"
        );

        section.getChildren().addAll(titleLabel, descContent);
        return section;
    }

    //Crea la sección de fecha límite.
    private VBox createDueDateSection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Fecha Límite");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        VBox dateBox = new VBox(5);
        dateBox.setStyle("-fx-padding: 12; -fx-background-color: #f9fafb; -fx-background-radius: 8;");

        Label dateLabel = new Label("📅 " + task.getFechaLimiteFormatted());
        dateLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");
        UrgencyInfo urgency = TaskUrgencyHelper.getUrgencyInfo(task.getFechaLimite());
        Label urgencyLabel = new Label(urgency.getFullText());
        urgencyLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + urgency.getColor() + "; -fx-font-weight: bold;");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(350);
        progressBar.setPrefHeight(8);
        progressBar.setProgress(urgency.getProgress());
        progressBar.setStyle("-fx-accent: " + urgency.getColor() + ";");
        dateBox.getChildren().addAll(dateLabel, urgencyLabel, progressBar);
        section.getChildren().addAll(titleLabel, dateBox);
        return section;
    }

    private VBox createAdditionalInfo() {
        VBox section = new VBox(5);
        Label infoLabel = new Label("💡 Haz clic en el checkbox para marcar como completada");
        infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #9ca3af; -fx-font-style: italic;");
        section.getChildren().add(infoLabel);
        return section;
    }
}
