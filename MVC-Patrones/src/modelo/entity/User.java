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
 *
 * @author samue
 */
public class User {

    private final StringProperty id;
    private final StringProperty nombreUsuario;
    private final StringProperty email;
    private final StringProperty passwordHash;
    private final ObjectProperty<LocalDateTime> fechaRegistro;


    public User(String nombreUsuario, String email, String password) {
        this(java.util.UUID.randomUUID().toString(), nombreUsuario, email, 
             hashPassword(password), LocalDateTime.now());
    }


    public User(String id, String nombreUsuario, String email, 
                String passwordHash, LocalDateTime fechaRegistro) {
        this.id = new SimpleStringProperty(id);
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        this.email = new SimpleStringProperty(email);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.fechaRegistro = new SimpleObjectProperty<>(fechaRegistro);
    }

    // Getters y Properties
    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public String getNombreUsuario() { return nombreUsuario.get(); }
    public void setNombreUsuario(String value) { nombreUsuario.set(value); }
    public StringProperty nombreUsuarioProperty() { return nombreUsuario; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    public String getPasswordHash() { return passwordHash.get(); }
    public StringProperty passwordHashProperty() { return passwordHash; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro.get(); }
    public ObjectProperty<LocalDateTime> fechaRegistroProperty() { return fechaRegistro; }

    // Verificación de contraseña
    public boolean verificarPassword(String password) {
        return hashPassword(password).equals(getPasswordHash());
    }

    public void cambiarPassword(String newPassword) {
        passwordHash.set(hashPassword(newPassword));
    }

    // Hash SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }

    // Serialización
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

    private static String safe(String value) {
        return (value != null) ? value : "";
    }
}
