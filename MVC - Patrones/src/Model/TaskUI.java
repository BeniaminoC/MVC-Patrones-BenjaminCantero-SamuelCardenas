/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
//Clase general con la info
/**
 *
 * @author BENJAMIN
 */
public class TaskUI {
    private String text;
    private String description;
    private java.time.LocalDate dueDate;
    private String priority;
    private boolean completed;
        
    public TaskUI(String text) {
        this(text, "", null, "OPCIONAL");
    }
        
    public TaskUI(String text, String description, java.time.LocalDate dueDate, String priority) {
        this.text = text;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }
        
    public String getText() { return text; }
    public String getDescription() { return description; }
    public java.time.LocalDate getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
        
    public String getDueDateFormatted() {
        if (dueDate == null) return "";
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dueDate.format(formatter);
    }
        
    public String getPriorityColor() {
        switch (priority) {
            case "URGENTE": return "#dc2626";
            case "IMPORTANTE": return "#f59e0b";
            case "OPCIONAL": return "#10b981";
            default: return "#6b7280";
        }
    }
        
    public String getPriorityBackgroundColor() {
        switch (priority) {
            case "URGENTE": return "#fee2e2";
            case "IMPORTANTE": return "#fef3c7";
            case "OPCIONAL": return "#d1fae5";
            default: return "#f3f4f6";
        }
    }
}

