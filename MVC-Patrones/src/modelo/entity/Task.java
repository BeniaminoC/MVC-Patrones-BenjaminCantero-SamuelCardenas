/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javafx.beans.property.*;

/**
 * Representa una tarea dentro del sistema, incluyendo información descriptiva,
 * estado de completitud, fechas clave, prioridad, etiquetas y asignación de
 * usuario.
 * <p>
 * La clase <code>Task</code> utiliza propiedades observables de JavaFX
 * ({@link StringProperty}, {@link BooleanProperty}, {@link ObjectProperty})
 * para facilitar el enlace directo entre el modelo de datos y la interfaz de
 * usuario.
 * </p>
 *
 * <p>
 * Incluye además utilidades para:
 * </p>
 * <ul>
 * <li>Formatear fechas de manera legible.</li>
 * <li>Serializar y deserializar tareas desde/ hacia texto plano.</li>
 * <li>Manejar valores nulos o vacíos de forma segura.</li>
 * </ul>
 *
 * <p>
 * Cada tarea tiene un identificador único generado mediante {@link UUID}.
 * </p>
 *
 * @author Samuel
 */
public class Task {

    /**
     * Identificador único de la tarea.
     */
    private final StringProperty id;

    /**
     * Título o nombre breve de la tarea.
     */
    private final StringProperty titulo;

    /**
     * Descripción detallada de la tarea.
     */
    private final StringProperty descripcion;

    /**
     * Indica si la tarea ha sido completada.
     */
    private final BooleanProperty completada;

    /**
     * Fecha y hora de creación de la tarea.
     */
    private final ObjectProperty<LocalDateTime> fechaCreacion;

    /**
     * Fecha límite o de vencimiento de la tarea.
     */
    private final ObjectProperty<LocalDate> fechaLimite;

    /**
     * Nombre del usuario asignado a la tarea.
     */
    private final StringProperty usuarioAsignado;

    /**
     * Prioridad de la tarea (por ejemplo: ALTA, MEDIA, BAJA, OPCIONAL).
     */
    private final StringProperty prioridad;

    /**
     * Etiquetas o tags asociados a la tarea, separados por comas.
     */
    private final StringProperty etiquetas;

    // -------------------------------------------------------------------------
    // Constructores
    // -------------------------------------------------------------------------
    /**
     * Crea una nueva tarea generando un ID único y estableciendo la fecha de
     * creación.
     *
     * @param titulo Título de la tarea.
     * @param descripcion Descripción breve de la tarea.
     * @param fechaLimite Fecha límite o de vencimiento.
     * @param prioridad Nivel de prioridad (por ejemplo, "ALTA" o "BAJA").
     */
    public Task(String titulo, String descripcion, LocalDate fechaLimite, String prioridad) {
        this(UUID.randomUUID().toString(), titulo, descripcion, false,
                LocalDateTime.now(), fechaLimite, null, prioridad, "");
    }

    /**
     * Crea una instancia completa de <code>Task</code> con todos sus atributos.
     *
     * @param id Identificador único.
     * @param titulo Título o nombre de la tarea.
     * @param descripcion Descripción textual.
     * @param completada Estado de finalización.
     * @param fechaCreacion Fecha y hora de creación.
     * @param fechaLimite Fecha límite o de vencimiento.
     * @param usuarioAsignado Usuario asignado a la tarea.
     * @param prioridad Nivel de prioridad (en mayúsculas).
     * @param etiquetas Conjunto de etiquetas separadas por comas.
     */
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
        this.prioridad = new SimpleStringProperty(prioridad != null ? prioridad.toUpperCase() : "OPCIONAL");
        this.etiquetas = new SimpleStringProperty(etiquetas != null ? etiquetas : "");
    }

    // -------------------------------------------------------------------------
    // Getters, Setters y Propiedades observables
    // -------------------------------------------------------------------------
    /**
     * @return Identificador único de la tarea.
     */
    public String getId() {
        return id.get();
    }

    /**
     * @return Propiedad observable del ID.
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * @return Título de la tarea.
     */
    public String getTitulo() {
        return titulo.get();
    }

    /**
     * Establece un nuevo título para la tarea.
     */
    public void setTitulo(String value) {
        titulo.set(value);
    }

    /**
     * @return Propiedad observable del título.
     */
    public StringProperty tituloProperty() {
        return titulo;
    }

    /**
     * @return Descripción detallada.
     */
    public String getDescripcion() {
        return descripcion.get();
    }

    /**
     * Establece una nueva descripción.
     */
    public void setDescripcion(String value) {
        descripcion.set(value);
    }

    /**
     * @return Propiedad observable de la descripción.
     */
    public StringProperty descripcionProperty() {
        return descripcion;
    }

    /**
     * @return <code>true</code> si la tarea está completada.
     */
    public boolean isCompletada() {
        return completada.get();
    }

    /**
     * Cambia el estado de completitud.
     */
    public void setCompletada(boolean value) {
        completada.set(value);
    }

    /**
     * @return Propiedad observable del estado de completitud.
     */
    public BooleanProperty completadaProperty() {
        return completada;
    }

    /**
     * @return Fecha y hora de creación.
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion.get();
    }

    /**
     * Establece una nueva fecha de creación.
     */
    public void setFechaCreacion(LocalDateTime value) {
        fechaCreacion.set(value);
    }

    /**
     * @return Propiedad observable de la fecha de creación.
     */
    public ObjectProperty<LocalDateTime> fechaCreacionProperty() {
        return fechaCreacion;
    }

    /**
     * @return Fecha límite o de vencimiento.
     */
    public LocalDate getFechaLimite() {
        return fechaLimite.get();
    }

    /**
     * Establece una nueva fecha límite.
     */
    public void setFechaLimite(LocalDate value) {
        fechaLimite.set(value);
    }

    /**
     * @return Propiedad observable de la fecha límite.
     */
    public ObjectProperty<LocalDate> fechaLimiteProperty() {
        return fechaLimite;
    }

    /**
     * @return Nombre del usuario asignado.
     */
    public String getUsuarioAsignado() {
        return usuarioAsignado.get();
    }

    /**
     * Asigna un usuario responsable.
     */
    public void setUsuarioAsignado(String value) {
        usuarioAsignado.set(value);
    }

    /**
     * @return Propiedad observable del usuario asignado.
     */
    public StringProperty usuarioAsignadoProperty() {
        return usuarioAsignado;
    }

    /**
     * @return Nivel de prioridad en texto.
     */
    public String getPrioridad() {
        return prioridad.get();
    }

    /**
     * Establece la prioridad de la tarea en mayúsculas.
     */
    public void setPrioridad(String value) {
        prioridad.set(value.toUpperCase());
    }

    /**
     * @return Propiedad observable de la prioridad.
     */
    public StringProperty prioridadProperty() {
        return prioridad;
    }

    /**
     * @return Etiquetas separadas por comas.
     */
    public String getEtiquetas() {
        return etiquetas.get();
    }

    /**
     * Define las etiquetas asociadas.
     */
    public void setEtiquetas(String value) {
        etiquetas.set(value);
    }

    /**
     * @return Propiedad observable de las etiquetas.
     */
    public StringProperty etiquetasProperty() {
        return etiquetas;
    }

    // -------------------------------------------------------------------------
    // Métodos utilitarios
    // -------------------------------------------------------------------------
    /**
     * Devuelve la fecha límite en formato <code>dd/MM/yyyy</code>. Si no hay
     * fecha definida, retorna una cadena vacía.
     *
     * @return Fecha límite formateada o cadena vacía.
     */
    public String getFechaLimiteFormatted() {
        if (getFechaLimite() == null) {
            return "";
        }
        return getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // -------------------------------------------------------------------------
    // Serialización
    // -------------------------------------------------------------------------
    /**
     * Serializa la tarea a una cadena de texto separada por punto y coma.
     * <p>
     * El formato generado incluye todos los campos relevantes, usando
     * {@link DateTimeFormatter#ISO_LOCAL_DATE_TIME} y
     * {@link DateTimeFormatter#ISO_LOCAL_DATE}.
     * </p>
     *
     * @return Representación en texto plano de la tarea.
     */
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

    /**
     * Crea una instancia de {@link Task} a partir de su representación textual.
     * <p>
     * Si la cadena está malformada, devuelve <code>null</code> y muestra un
     * mensaje de error en la salida estándar.
     * </p>
     *
     * @param linea Cadena de texto con los datos de la tarea.
     * @return Objeto <code>Task</code> generado o <code>null</code> si ocurre
     * un error.
     */
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

    /**
     * Retorna una cadena vacía en caso de valor nulo.
     *
     * @param value Valor de entrada.
     * @return Cadena no nula.
     */
    private static String safe(String value) {
        return (value != null) ? value : "";
    }
}
