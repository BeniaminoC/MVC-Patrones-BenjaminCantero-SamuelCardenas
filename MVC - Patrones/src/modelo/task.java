/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.io.Serializable;
/**
 *
 * @author samue
 */
public class task implements Serializable{
    
    private int id;
    private String titulo;
    private String descripcion;
    private String estado; // Ejemplo: "Pendiente", "Completada", "Incompleta"

    public task(int id, String titulo, String descripcion, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override 
    public String toString() {
        return id + ";" + titulo + ";" + descripcion + ";" + estado; 
    }
    
// Convierte una línea de texto del archivo en un objeto task
   
    public static task fromString(String data){
    String[] partes = data.split(";");
    if (partes.length < 4){
       throw new IllegalArgumentException("Formato de tarea inválido: " + data);   
    }
    int id = Integer.parseInt(partes[0]);
    return new task(id, partes[1], partes[2], partes[3]);
}
    
    
}
