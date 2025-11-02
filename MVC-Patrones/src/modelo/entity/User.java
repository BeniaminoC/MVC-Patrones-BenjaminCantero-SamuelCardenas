/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javafx.beans.property.*;

/**
 * Representa un usuario dentro del sistema de gestión de tareas.
 * <p>
 * La clase <code>User</code> encapsula la información básica de un usuario, 
 * incluyendo su identificador único, nombre de usuario, correo electrónico, 
 * contraseña cifrada y fecha de registro.
 * </p>
 *
 * <p>
 * Utiliza propiedades observables de JavaFX ({@link StringProperty}, 
 * {@link ObjectProperty}) para permitir la vinculación directa con la interfaz 
 * gráfica, facilitando la actualización dinámica de datos en vistas.
 * </p>
 *
 * <h3>Seguridad</h3>
 * <p>
 * Las contraseñas no se almacenan en texto plano. Se aplica un proceso de 
 * <b>hash SHA-256</b> codificado en Base64 mediante el método 
 * {@link #hashPassword(String)}, lo que mejora la seguridad y evita la 
 * exposición directa de credenciales.
 * </p>
 * 
 * @author Samuel
 */
public class User {

    /** Identificador único del usuario (UUID). */
    private final StringProperty id;

    /** Nombre de usuario utilizado para autenticación. */
    private final StringProperty nombreUsuario;

    /** Dirección de correo electrónico del usuario. */
    private final StringProperty email;

    /** Contraseña almacenada en formato hash (SHA-256 codificada en Base64). */
    private final StringProperty passwordHash;

    /** Fecha y hora en que el usuario se registró en el sistema. */
    private final ObjectProperty<LocalDateTime> fechaRegistro;

    /**
     * Crea un nuevo usuario con los datos básicos y genera automáticamente
     * un identificador único y la fecha de registro actual.
     *
     * @param nombreUsuario Nombre de usuario.
     * @param email Correo electrónico.
     * @param password Contraseña en texto plano (será convertida a hash).
     */
    public User(String nombreUsuario, String email, String password) {
        this(java.util.UUID.randomUUID().toString(), nombreUsuario, email,
             hashPassword(password), LocalDateTime.now());
    }

    /**
     * Crea un usuario con todos los campos especificados, 
     * útil para procesos de carga o deserialización.
     *
     * @param id Identificador único del usuario.
     * @param nombreUsuario Nombre de usuario.
     * @param email Correo electrónico.
     * @param passwordHash Contraseña ya hasheada.
     * @param fechaRegistro Fecha de registro del usuario.
     */
    public User(String id, String nombreUsuario, String email,
                String passwordHash, LocalDateTime fechaRegistro) {
        this.id = new SimpleStringProperty(id);
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        this.email = new SimpleStringProperty(email);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.fechaRegistro = new SimpleObjectProperty<>(fechaRegistro);
    }

    // ==============================
    // Getters y Properties
    // ==============================

    /** @return Identificador único del usuario. */
    public String getId() { return id.get(); }

    /** @return Propiedad observable del identificador. */
    public StringProperty idProperty() { return id; }

    /** @return Nombre de usuario. */
    public String getNombreUsuario() { return nombreUsuario.get(); }

    /** Establece el nombre de usuario. */
    public void setNombreUsuario(String value) { nombreUsuario.set(value); }

    /** @return Propiedad observable del nombre de usuario. */
    public StringProperty nombreUsuarioProperty() { return nombreUsuario; }

    /** @return Correo electrónico del usuario. */
    public String getEmail() { return email.get(); }

    /** Establece el correo electrónico. */
    public void setEmail(String value) { email.set(value); }

    /** @return Propiedad observable del correo electrónico. */
    public StringProperty emailProperty() { return email; }

    /** @return Contraseña en formato hash (SHA-256). */
    public String getPasswordHash() { return passwordHash.get(); }

    /** @return Propiedad observable del hash de la contraseña. */
    public StringProperty passwordHashProperty() { return passwordHash; }

    /** @return Fecha y hora de registro del usuario. */
    public LocalDateTime getFechaRegistro() { return fechaRegistro.get(); }

    /** @return Propiedad observable de la fecha de registro. */
    public ObjectProperty<LocalDateTime> fechaRegistroProperty() { return fechaRegistro; }

    // ==============================
    // Seguridad y validación
    // ==============================

    /**
     * Verifica si una contraseña ingresada coincide con el hash almacenado.
     *
     * @param password Contraseña en texto plano a verificar.
     * @return {@code true} si la contraseña coincide; {@code false} en caso contrario.
     */
    public boolean verificarPassword(String password) {
        return hashPassword(password).equals(getPasswordHash());
    }

    /**
     * Cambia la contraseña del usuario, aplicando nuevamente el hash SHA-256.
     *
     * @param newPassword Nueva contraseña en texto plano.
     */
    public void cambiarPassword(String newPassword) {
        passwordHash.set(hashPassword(newPassword));
    }

    /**
     * Genera el hash SHA-256 codificado en Base64 de una contraseña.
     *
     * @param password Contraseña en texto plano.
     * @return Contraseña cifrada en formato Base64.
     * @throws RuntimeException si el algoritmo SHA-256 no está disponible.
     */
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }

    // ==============================
    // Serialización
    // ==============================

    /**
     * Convierte el usuario en una cadena separada por punto y coma, 
     * útil para persistencia en archivos de texto.
     *
     * @return Representación serializada del usuario.
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String fechaStr = (getFechaRegistro() != null) ? getFechaRegistro().format(fmt) : "";

        return String.join(";",
            safe(getId()),
            safe(getNombreUsuario()),
            safe(getEmail()),
            safe(getPasswordHash()),
            fechaStr
        );
    }

    /**
     * Reconstruye un objeto {@link User} a partir de su representación en texto.
     *
     * @param linea Cadena con los campos separados por punto y coma.
     * @return Instancia de {@link User} o {@code null} si ocurre un error.
     */
    public static User fromString(String linea) {
        try {
            String[] p = linea.split(";", -1);
            DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            LocalDateTime fecha = (p.length > 4 && !p[4].isEmpty())
                ? LocalDateTime.parse(p[4], fmt) : LocalDateTime.now();

            return new User(
                p.length > 0 ? p[0] : java.util.UUID.randomUUID().toString(),
                p.length > 1 ? p[1] : "",
                p.length > 2 ? p[2] : "",
                p.length > 3 ? p[3] : "",
                fecha
            );
        } catch (Exception e) {
            System.err.println("Error al parsear usuario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Evita valores nulos al convertir cadenas en texto.
     *
     * @param value Valor a validar.
     * @return Cadena vacía si el valor es {@code null}.
     */
    private static String safe(String value) {
        return (value != null) ? value : "";
    }
}
