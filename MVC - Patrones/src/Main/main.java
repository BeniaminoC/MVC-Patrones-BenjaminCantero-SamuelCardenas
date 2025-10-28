/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;
import modelo.*;
/**
 *
 * @author BENJAMIN
 */
public class main {
    public static void main(String[] args) {
        fileManager fm = new fileManager();
        System.out.println("Tareas cargadas desde archivo:");
        fm.loadTasks().forEach(System.out::println);
    }
    
}
