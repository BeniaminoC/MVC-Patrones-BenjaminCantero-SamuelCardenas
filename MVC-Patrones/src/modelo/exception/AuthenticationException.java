/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 *
 * @author samue
 */
public class AuthenticationException extends ModelException {

    public AuthenticationException() {
        super("Usuario o contraseña incorrectos.");
    }
}
