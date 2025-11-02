/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 * Excepción que se lanza cuando falla el proceso de autenticación de un
 * usuario.
 * <p>
 * Esta excepción es utilizada para indicar que las credenciales proporcionadas
 * (nombre de usuario o contraseña) no son válidas, evitando así el acceso no
 * autorizado al sistema.
 * </p>
 *
 * <p>
 * Normalmente es lanzada por los componentes de autenticación o servicios de
 * inicio de sesión al detectar credenciales incorrectas.
 * </p>
 *
 * @author samue
 */
public class AuthenticationException extends ModelException {

    /**
     * Crea una nueva excepción de autenticación con un mensaje predeterminado
     * que indica que el usuario o la contraseña son incorrectos.
     */
    public AuthenticationException() {
        super("Usuario o contraseña incorrectos.");
    }
}
