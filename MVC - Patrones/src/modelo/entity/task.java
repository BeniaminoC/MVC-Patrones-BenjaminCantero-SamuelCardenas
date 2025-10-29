/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entity;
/**
 * +
 *
 * @author samue
 */
import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private final StringProperty titulo;
    private final StringProperty descripcion;
    private final BooleanProperty completada;
    private final ObjectProperty<LocalDate> fechaCreacion;
    private final ObjectProperty<LocalDate> fechaLimite;
    private final StringProperty usuarioAsignado;

    public Task(String titulo, String descripcion) {
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.completada = new SimpleBooleanProperty(false);
        this.fechaCreacion = new SimpleObjectProperty<>(LocalDate.now());
        this.fechaLimite = new SimpleObjectProperty<>(null);
        this.usuarioAsignado = new SimpleStringProperty(null);
    }

    public Task(String titulo, String descripcion, boolean completada,
            LocalDate fechaCreacion, LocalDate fechaLimite, String usuarioAsignado) {
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.completada = new SimpleBooleanProperty(completada);
        this.fechaCreacion = new SimpleObjectProperty<>(fechaCreacion);
        this.fechaLimite = new SimpleObjectProperty<>(fechaLimite);
        this.usuarioAsignado = new SimpleStringProperty(usuarioAsignado);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String value) {
        titulo.set(value);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String value) {
        descripcion.set(value);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public boolean isCompletada() {
        return completada.get();
    }

    public void setCompletada(boolean value) {
        completada.set(value);
    }

    public BooleanProperty completadaProperty() {
        return completada;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion.get();
    }

    public void setFechaCreacion(LocalDate value) {
        fechaCreacion.set(value);
    }

    public ObjectProperty<LocalDate> fechaCreacionProperty() {
        return fechaCreacion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite.get();
    }

    public void setFechaLimite(LocalDate value) {
        fechaLimite.set(value);
    }

    public ObjectProperty<LocalDate> fechaLimiteProperty() {
        return fechaLimite;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado.get();
    }

    public void setUsuarioAsignado(String value) {
        usuarioAsignado.set(value);
    }

    public StringProperty usuarioAsignadoProperty() {
        return usuarioAsignado;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        String creacionStr = (getFechaCreacion() != null) ? getFechaCreacion().format(fmt) : "";
        String limiteStr = (getFechaLimite() != null) ? getFechaLimite().format(fmt) : "";
        return String.join(";",
                getTitulo(),
                getDescripcion(),
                String.valueOf(isCompletada()),
                creacionStr,
                limiteStr,
                getUsuarioAsignado() != null ? getUsuarioAsignado() : "");
    }

    public static Task fromString(String linea) {
        try {
            String[] p = linea.split(";");
            DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate fCrea = p[3].isEmpty() ? null : LocalDate.parse(p[3], fmt);
            LocalDate fLim = p[4].isEmpty() ? null : LocalDate.parse(p[4], fmt);
            return new Task(
                    p[0],
                    p[1],
                    Boolean.parseBoolean(p[2]),
                    fCrea,
                    fLim,
                    p.length > 5 ? p[5] : null
            );
        } catch (Exception e) {
            System.err.println("Error al parsear tarea: " + e.getMessage());
            return null;
        }
    }
}
