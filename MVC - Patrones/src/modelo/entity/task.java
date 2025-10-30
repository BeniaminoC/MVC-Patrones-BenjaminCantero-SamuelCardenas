/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entity;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Entidad unificada de Tarea
 * Combina funcionalidad de Task y TaskUI con properties observables
 */
public class Task {
    
    private final StringProperty id;
    private final StringProperty titulo;
    private final StringProperty descripcion;
    private final BooleanProperty completada;
    private final ObjectProperty<LocalDateTime> fechaCreacion;
    private final ObjectProperty<LocalDate> fechaLimite;
    private final StringProperty usuarioAsignado;
    private final StringProperty prioridad;
    private final StringProperty etiquetas; // tags separados por comas

    // Constructor para nuevas tareas
    public Task(String titulo, String descripcion, LocalDate fechaLimite, String prioridad) {
        this(UUID.randomUUID().toString(), titulo, descripcion, false, 
             LocalDateTime.now(), fechaLimite, null, prioridad, "");
    }

    // Constructor completo
    public Task(String id, String titulo, String descripcion, boolean completada,
                LocalDateTime fechaCreacion, LocalDate fechaLimite,
                String usuarioAsignado, String prioridad, String etiquetas) {
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo != null ? titulo : "");
        this.descripcion = new SimpleStringProperty(descripcion != null ? descripcion : "");
        this.completada = new SimpleBooleanProperty(completada);
        this.fechaCreacion = new SimpleObjectProperty<>(fechaCreacion);
        this.fechaLimite = new SimpleObjectProperty<>(fechaLimite);
        this.usuarioAsignado = new SimpleStringProperty(usuarioAsignado);
        this.prioridad = new SimpleStringProperty(
            prioridad != null ? prioridad.toUpperCase() : "OPCIONAL");
        this.etiquetas = new SimpleStringProperty(etiquetas != null ? etiquetas : "");
    }

    // Getters y Properties
    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public String getTitulo() { return titulo.get(); }
    public void setTitulo(String value) { titulo.set(value); }
    public StringProperty tituloProperty() { return titulo; }

    public String getDescripcion() { return descripcion.get(); }
    public void setDescripcion(String value) { descripcion.set(value); }
    public StringProperty descripcionProperty() { return descripcion; }

    public boolean isCompletada() { return completada.get(); }
    public void setCompletada(boolean value) { completada.set(value); }
    public BooleanProperty completadaProperty() { return completada; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion.get(); }
    public void setFechaCreacion(LocalDateTime value) { fechaCreacion.set(value); }
    public ObjectProperty<LocalDateTime> fechaCreacionProperty() { return fechaCreacion; }

    public LocalDate getFechaLimite() { return fechaLimite.get(); }
    public void setFechaLimite(LocalDate value) { fechaLimite.set(value); }
    public ObjectProperty<LocalDate> fechaLimiteProperty() { return fechaLimite; }

    public String getUsuarioAsignado() { return usuarioAsignado.get(); }
    public void setUsuarioAsignado(String value) { usuarioAsignado.set(value); }
    public StringProperty usuarioAsignadoProperty() { return usuarioAsignado; }

    public String getPrioridad() { return prioridad.get(); }
    public void setPrioridad(String value) { prioridad.set(value.toUpperCase()); }
    public StringProperty prioridadProperty() { return prioridad; }

    public String getEtiquetas() { return etiquetas.get(); }
    public void setEtiquetas(String value) { etiquetas.set(value); }
    public StringProperty etiquetasProperty() { return etiquetas; }

    // Métodos de presentación
    public String getFechaLimiteFormatted() {
        if (getFechaLimite() == null) return "";
        return getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getPriorityColor() {
        switch (getPrioridad()) {
            case "URGENTE": return "#dc2626";
            case "IMPORTANTE": return "#f59e0b";
            case "OPCIONAL": return "#10b981";
            default: return "#6b7280";
        }
    }

    public String getPriorityBackgroundColor() {
        switch (getPrioridad()) {
            case "URGENTE": return "#fee2e2";
            case "IMPORTANTE": return "#fef3c7";
            case "OPCIONAL": return "#d1fae5";
            default: return "#f3f4f6";
        }
    }

    // Serialización
    @Override
    public String toString() {
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter fmtDate = DateTimeFormatter.ISO_LOCAL_DATE;
        
        String creacionStr = (getFechaCreacion() != null) ? getFechaCreacion().format(fmtDateTime) : "";
        String limiteStr = (getFechaLimite() != null) ? getFechaLimite().format(fmtDate) : "";
        
        return String.join(";",
            safe(getId()),
            safe(getTitulo()),
            safe(getDescripcion()),
            String.valueOf(isCompletada()),
            creacionStr,
            limiteStr,
            safe(getUsuarioAsignado()),
            safe(getPrioridad()),
            safe(getEtiquetas())
        );
    }

    public static Task fromString(String linea) {
        try {
            String[] p = linea.split(";", -1);
            DateTimeFormatter fmtDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            DateTimeFormatter fmtDate = DateTimeFormatter.ISO_LOCAL_DATE;
            
            LocalDateTime fCrea = (p.length > 4 && !p[4].isEmpty()) 
                ? LocalDateTime.parse(p[4], fmtDateTime) : LocalDateTime.now();
            LocalDate fLim = (p.length > 5 && !p[5].isEmpty()) 
                ? LocalDate.parse(p[5], fmtDate) : null;

            return new Task(
                p.length > 0 ? p[0] : UUID.randomUUID().toString(),
                p.length > 1 ? p[1] : "",
                p.length > 2 ? p[2] : "",
                p.length > 3 && Boolean.parseBoolean(p[3]),
                fCrea,
                fLim,
                p.length > 6 ? p[6] : null,
                p.length > 7 ? p[7] : "OPCIONAL",
                p.length > 8 ? p[8] : ""
            );
        } catch (Exception e) {
            System.err.println("Error al parsear tarea: " + e.getMessage());
            return null;
        }
    }

    private static String safe(String value) {
        return (value != null) ? value : "";
    }
}