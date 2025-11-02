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
 * Diálogo modal que muestra información detallada de una tarea.
 * <p>
 * {@code DialogDetails} extiende {@link javafx.scene.control.Dialog} para
 * presentar una ventana emergente con todos los detalles de una tarea,
 * incluyendo su estado, prioridad, descripción, fecha límite y nivel de urgencia.
 * </p>
 * 
 * <p>
 * El diálogo se construye dinámicamente en función de los datos de la tarea,
 * mostrando solo las secciones relevantes (por ejemplo, omite la descripción
 * si está vacía o la fecha límite si no está definida).
 * </p>
 * 
 * <h3>Características visuales:</h3>
 * <ul>
 *   <li>Encabezado coloreado según la prioridad de la tarea</li>
 *   <li>Iconos y etiquetas visuales para estado y urgencia</li>
 *   <li>Barra de progreso para visualizar tiempo restante</li>
 *   <li>Diseño responsive con estilos CSS personalizados</li>
 *   <li>Separadores y espaciado consistente</li>
 * </ul>
 * 
 * <h3>Secciones del diálogo:</h3>
 * <ol>
 *   <li><b>Estado:</b> Muestra si la tarea está completada o pendiente</li>
 *   <li><b>Prioridad:</b> Badge coloreado con el nivel de prioridad</li>
 *   <li><b>Descripción:</b> Texto descriptivo de la tarea (si existe)</li>
 *   <li><b>Fecha Límite:</b> Fecha, urgencia y barra de progreso (si existe)</li>
 *   <li><b>Información adicional:</b> Instrucciones y sugerencias</li>
 * </ol>
 * 
 *
 * @author BENJAMIN
 * @see Task
 * @see TaskStyleHelper
 * @see TaskUrgencyHelper
 */
public class DialogDetails extends javafx.scene.control.Dialog<Task> {

    /**
     * Tarea cuyos detalles serán mostrados en el diálogo.
     */
    private Task task;

    /**
     * Constructor que inicializa el diálogo con una tarea específica.
     * <p>
     * Automáticamente configura y construye todos los componentes visuales
     * del diálogo basándose en los datos de la tarea proporcionada.
     * </p>
     *
     * @param task tarea de la cual se mostrarán los detalles, no debe ser {@code null}
     */
    public DialogDetails(Task task) {
        this.task = task;
        initializeDialog();
    }

    /**
     * Inicializa y configura los componentes principales del diálogo.
     * <p>
     * Este método establece el título, estiliza el encabezado con el color
     * de prioridad de la tarea, construye el contenido y configura el botón
     * de cierre.
     * </p>
     * 
     * <p>
     * La estilización del encabezado se realiza en {@link javafx.application.Platform#runLater}
     * para asegurar que los nodos estén completamente renderizados antes de aplicar estilos.
     * </p>
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
     * Crea el contenedor principal con todas las secciones del diálogo.
     * <p>
     * Construye dinámicamente las secciones basándose en los datos disponibles
     * de la tarea. Las secciones opcionales (descripción y fecha límite) solo
     * se incluyen si tienen información válida.
     * </p>
     *
     * @return contenedor {@link VBox} con todas las secciones del diálogo
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
     * Crea la sección que muestra el estado de la tarea.
     * <p>
     * Incluye un icono visual (✓ para completada, ○ para pendiente) y
     * texto coloreado según el estado.
     * </p>
     *
     * @return contenedor {@link VBox} con la información del estado
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
     * <p>
     * La prioridad se presenta como un badge coloreado usando el color
     * correspondiente obtenido de {@link TaskStyleHelper#getPriorityColor(Task)}.
     * </p>
     *
     * @return contenedor {@link VBox} con el badge de prioridad
     */
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

    /**
     * Crea la sección que muestra la descripción detallada de la tarea.
     * <p>
     * El texto de la descripción se presenta con ajuste automático de línea
     * en un contenedor con fondo gris claro para mejor legibilidad.
     * </p>
     *
     * @return contenedor {@link VBox} con la descripción de la tarea
     */
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

    /**
     * Crea la sección que muestra la fecha límite y su nivel de urgencia.
     * <p>
     * Incluye:
     * <ul>
     *   <li>Fecha límite formateada con icono de calendario</li>
     *   <li>Etiqueta de urgencia con texto descriptivo y color</li>
     *   <li>Barra de progreso visual indicando tiempo restante</li>
     * </ul>
     * </p>
     * 
     * <p>
     * La información de urgencia se obtiene mediante
     * {@link TaskUrgencyHelper#getUrgencyInfo(LocalDate)}.
     * </p>
     *
     * @return contenedor {@link VBox} con la información de fecha límite y urgencia
     */
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

    /**
     * Crea la sección de información adicional con instrucciones para el usuario.
     * <p>
     * Muestra un mensaje informativo sobre cómo marcar la tarea como completada
     * utilizando el checkbox en la vista principal.
     * </p>
     *
     * @return contenedor {@link VBox} con el mensaje informativo
     */
    private VBox createAdditionalInfo() {
        VBox section = new VBox(5);
        Label infoLabel = new Label("💡 Haz clic en el checkbox para marcar como completada");
        infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #9ca3af; -fx-font-style: italic;");
        section.getChildren().add(infoLabel);
        return section;
    }
}