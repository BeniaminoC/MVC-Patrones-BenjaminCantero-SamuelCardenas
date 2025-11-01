/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

public class ExistingUserException extends ModelException {
    public ExistingUserException(String username) {
        super("El usuario '" + username + "' ya existe.");
    }
}