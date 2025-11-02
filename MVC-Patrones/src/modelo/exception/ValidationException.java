/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

/**
 * Excepción que se lanza cuando ocurre un error de validación de datos dentro
 * del modelo o en la capa de servicios.
 * <p>
 * Suele utilizarse para señalar datos incompletos, inválidos o inconsistentes
 * introducidos por el usuario o generados durante un proceso de negocio.
 * </p>
 *
 * @author samue
 *
 */
public class ValidationException extends ModelException {

    /**
     * Crea una nueva excepción de validación con un mensaje descriptivo.
     *
     * @param message descripción del error de validación
     */
    public ValidationException(String message) {
        super(message);
    }
}
