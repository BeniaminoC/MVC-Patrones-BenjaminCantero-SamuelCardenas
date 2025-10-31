/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.exception;

public class ExistingTaskException extends ModelException {
    public ExistingTaskException(String titulo) {
        super("La tarea '" + titulo + "' ya existe.");
    }
}