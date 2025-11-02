/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 * Clase base para todas las excepciones del modelo de la aplicación.
 * <p>
 * Esta clase extiende {@link Exception} y sirve como superclase para
 * excepciones personalizadas relacionadas con la lógica de negocio y la
 * persistencia de datos en el modelo.
 * </p>
 *
 * <p>
 * Su propósito es proporcionar un punto común de jerarquía para manejar
 * errores de manera uniforme en las capas superiores (servicios y controladores).
 * </p>
 *
 * @author samue
 */
public class ModelException extends Exception {

    /**
     * Crea una nueva excepción con un mensaje descriptivo.
     *
     * @param message descripción del error
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Crea una nueva excepción con un mensaje descriptivo y una causa subyacente.
     *
     * @param message descripción del error
     * @param cause   excepción original que causó este error
     */
    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
