/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import modelo.service.TaskService;
import modelo.service.UserService;
import modelo.exception.*;

public class TestModelo {
    public static void main(String[] args) {
        try {
            // Probar UserService
            UserService userService = new UserService();
            userService.registrarUsuario("admin", "1234");
            System.out.println("✅ Usuario creado exitosamente");
            
            // Probar TaskService
            TaskService taskService = new TaskService();
            taskService.crearTarea("Primera tarea", "Descripción de prueba");
            System.out.println("✅ Tarea creada exitosamente");
            
            // Listar tareas
            System.out.println("Tareas: " + taskService.obtenerTodasLasTareas().size());
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

}