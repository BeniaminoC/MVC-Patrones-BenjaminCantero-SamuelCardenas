/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final StringProperty nombreUsuario;
    private final StringProperty contraUser;

    // Constructor principal
    public User(String nombreUsuario, String contraUser) {
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        this.contraUser = new SimpleStringProperty(contraUser);
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public void setNombreUsuario(String value) {
        nombreUsuario.set(value);
    }

    public StringProperty nombreUsuarioProperty() {
        return nombreUsuario;
    }

    public String getContraUser() { // Cambiado de getContrauser a getContraUser
        return contraUser.get();
    }

    public void setContraUser(String value) {
        contraUser.set(value);
    }

    public StringProperty contraUserProperty() { // Corregido el nombre
        return contraUser;
    }

    //  Serialización simple
    @Override
    public String toString() {
        return String.join(";", getNombreUsuario(), getContraUser());
    }

    public static User fromString(String linea) {
        String[] p = linea.split(";");
        if (p.length < 2) {
            return null;
        }
        return new User(p[0], p[1]);
    }
}