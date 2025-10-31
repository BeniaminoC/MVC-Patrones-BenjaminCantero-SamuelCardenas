/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.dialogs;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import modelo.entity.Task;
import Controller.util.TaskStyleHelper; // <-- import agregado

/**
 * {@code DialogDetails} representa un cuadro de diálogo personalizado que muestra los detalles
 * completos de una tarea ({@link Task}) en la aplicación.
 */
public class DialogDetails extends javafx.scene.control.Dialog<Task> {

    /** Tarea asociada cuyos detalles se mostrarán. */
    private Task task;

    /**
     * Crea una nueva instancia del cuadro de diálogo con los detalles de la tarea.
     *
     * @param task objeto {@link Task} del cual se mostrarán los detalles
     */
    public DialogDetails(Task task) {
        this.task = task;
        initializeDialog();
    }

    /**
     * Configura el diálogo principal, aplicando título, cabecera, contenido y estilos.
     */
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

    /**
     * Construye y organiza todas las secciones visuales del contenido del diálogo.
     *
     * @return contenedor principal del contenido
     */
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

    /**
     * Crea la sección que muestra el estado actual de la tarea.
     *
     * @return contenedor con el estado y su etiqueta
     */
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

    /**
     * Crea la sección que muestra la prioridad de la tarea.
     *
     * @return contenedor con la prioridad
     */
    private VBox createPrioritySection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Prioridad");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        Label priorityBadge = new Label(task.getPrioridad());
        priorityBadge.setStyle(
            "-fx-background-color: " + TaskStyleHelper.getPriorityColor(task) + "; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 8 16; " +
            "-fx-background-radius: 20; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold;"
        );

        section.getChildren().addAll(titleLabel, priorityBadge);
        return section;
    }

    /**
     * Crea la sección que muestra la descripción de la tarea.
     *
     * @return contenedor con la descripción
     */
    private VBox createDescriptionSection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Descripción");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        Label descContent = new Label(task.getDescripcion());
        descContent.setWrapText(true);
        descContent.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-text-fill: #374151; " +
            "-fx-padding: 12; " +
            "-fx-background-color: #f9fafb; " +
            "-fx-background-radius: 8;"
        );

        section.getChildren().addAll(titleLabel, descContent);
        return section;
    }

    /**
     * Crea la sección que muestra la fecha límite y el progreso según la urgencia.
     *
     * @return contenedor con la información de fecha límite
     */
    private VBox createDueDateSection() {
        VBox section = new VBox(8);

        Label titleLabel = new Label("Fecha Límite");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: bold;");

        VBox dateBox = new VBox(5);
        dateBox.setStyle("-fx-padding: 12; -fx-background-color: #f9fafb; -fx-background-radius: 8;");

        Label dateLabel = new Label("📅 " + task.getFechaLimiteFormatted());
        dateLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), task.getFechaLimite());
        String daysText;
        String urgencyColor;

        if (daysUntil < 0) {
            daysText = "⚠️ Vencida hace " + Math.abs(daysUntil) + " día" + (Math.abs(daysUntil) == 1 ? "" : "s");
            urgencyColor = "#dc2626";
        } else if (daysUntil == 0) {
            daysText = "⏰ Vence HOY";
            urgencyColor = "#f59e0b";
        } else if (daysUntil == 1) {
            daysText = "⏰ Vence MAÑANA";
            urgencyColor = "#f59e0b";
        } else if (daysUntil <= 3) {
            daysText = "⏳ Faltan " + daysUntil + " días";
            urgencyColor = "#f59e0b";
        } else if (daysUntil <= 7) {
            daysText = "📊 Faltan " + daysUntil + " días";
            urgencyColor = "#3b82f6";
        } else {
            daysText = "✓ Faltan " + daysUntil + " días";
            urgencyColor = "#10b981";
        }

        Label urgencyLabel = new Label(daysText);
        urgencyLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + urgencyColor + "; -fx-font-weight: bold;");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(350);
        progressBar.setPrefHeight(8);

        double progress;
        if (daysUntil < 0) {
            progress = 1.0;
        } else if (daysUntil <= 7) {
            progress = 1.0 - (daysUntil / 7.0);
        } else {
            progress = 0.0;
        }

        progressBar.setProgress(progress);
        progressBar.setStyle("-fx-accent: " + urgencyColor + ";");

        dateBox.getChildren().addAll(dateLabel, urgencyLabel, progressBar);
        section.getChildren().addAll(titleLabel, dateBox);
        return section;
    }

    /**
     * Crea una sección con información adicional o consejos para el usuario.
     *
     * @return contenedor con mensaje adicional
     */
    private VBox createAdditionalInfo() {
        VBox section = new VBox(5);

        Label infoLabel = new Label("💡 Haz clic en el checkbox para marcar como completada");
        infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #9ca3af; -fx-font-style: italic;");

        section.getChildren().add(infoLabel);
        return section;
    }
}
