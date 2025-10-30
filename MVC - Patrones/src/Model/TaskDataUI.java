/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
//Clase de datos para dialogos
/**
 *
 * @author BENJAMIN
 */
public class TaskDataUI {
    private String title;
    private String description;
    private java.time.LocalDate dueDate;
    private String priority;
        
    public TaskDataUI(String title, String description, java.time.LocalDate dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }
        
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public java.time.LocalDate getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
    
}
