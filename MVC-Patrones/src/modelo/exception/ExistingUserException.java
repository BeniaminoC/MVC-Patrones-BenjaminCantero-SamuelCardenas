/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 * Excepción que indica que un usuario ya existe en el sistema.
 * <p>
 * Se utiliza al intentar registrar un nuevo usuario con un nombre o correo que
 * ya se encuentra registrado en el repositorio.
 * </p>
 *
 * @author samue
 */
public class ExistingUserException extends ModelException {

    /**
     * Crea una nueva excepción indicando que el usuario especificado ya existe.
     *
     * @param username nombre del usuario duplicado
     */
    public ExistingUserException(String username) {
        super("El usuario '" + username + "' ya existe.");
    }
}
