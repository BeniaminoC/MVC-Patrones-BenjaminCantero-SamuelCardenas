/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repository;

import modelo.entity.Task;
import modelo.persistence.FileManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepository {

    private static TaskRepository instance;     // instancia única
    private final FileManager fileManager;      // gestor de persistencia
    private final List<Task> tareas;            // lista en memoria

    /**
     * Constructor privado: carga tareas existentes desde el archivo.
     */
    private TaskRepository() {
        this.fileManager = new FileManager();
        this.tareas = new ArrayList<>(fileManager.loadTasks());
    }

    /**
     * Devuelve la instancia única del repositorio.
     */
    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    /**
     * Guarda una nueva tarea y actualiza el archivo.
     */
    public void save(Task task) {
        tareas.add(task);
        fileManager.saveTasks(tareas);
    }

    /**
     * Retorna todas las tareas cargadas.
     */
    public List<Task> findAll() {
        return new ArrayList<>(tareas); // copia defensiva
    }

    /**
     * Elimina una tarea y actualiza el archivo.
     */
    public void delete(Task task) {
        tareas.remove(task);
        fileManager.saveTasks(tareas);
    }

    /**
     * Fuerza la escritura del estado actual al archivo.
     */
    public void update() {
        fileManager.saveTasks(tareas);
    }

    /**
     * Limpia todas las tareas (solo para pruebas o debug).
     */
    public void clearAll() {
        tareas.clear();
        fileManager.saveTasks(tareas);
    }

    public Optional<Task> findByTitulo(String titulo) {
        return tareas.stream()
                .filter(task -> task.getTitulo().equals(titulo))
                .findFirst();
    }

    public List<Task> findPendientes() {
        return tareas.stream()
                .filter(task -> !task.isCompletada())
                .collect(Collectors.toList());
    }

    public List<Task> findCompletadas() {
        return tareas.stream()
                .filter(Task::isCompletada)
                .collect(Collectors.toList());
    }

    public List<Task> findByUsuario(String username) {
        return tareas.stream()
                .filter(task -> username.equals(task.getUsuarioAsignado()))
                .collect(Collectors.toList());
    }
}