/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import modelo.entity.Task;
import modelo.exception.*;
import modelo.repository.TaskRepository;

/**
 *
 * @author samue
 */
public class TaskService {

    private final TaskRepository repository;

    public TaskService() {
        this.repository = TaskRepository.getInstance();
    }

    public Task crearTarea(String titulo, String descripcion, 
                          LocalDate fechaLimite, String prioridad, 
                          String usuarioAsignado) throws ValidationException, ExistingTaskException {
        
        // Validaciones
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }
        
        if (titulo.length() > 100) {
            throw new ValidationException("El título no puede exceder 100 caracteres");
        }

        // Verificar duplicados
        Optional<Task> existing = repository.findByTitulo(titulo);
        if (existing.isPresent() && 
            usuarioAsignado.equals(existing.get().getUsuarioAsignado())) {
            throw new ExistingTaskException(titulo);
        }

        // Crear tarea
        Task task = new Task(titulo, descripcion, fechaLimite, prioridad);
        task.setUsuarioAsignado(usuarioAsignado);
        
        repository.save(task);
        return task;
    }

    public void actualizarTarea(Task task) throws ValidationException {
        if (task.getTitulo() == null || task.getTitulo().trim().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }
        repository.update(task);
    }

    public void eliminarTarea(Task task) {
        repository.delete(task);
    }

    public void marcarCompletada(Task task, boolean completada) {
        task.setCompletada(completada);
        repository.update(task);
    }
    
    public List<Task> obtenerTareasUsuario(String username) {
        return repository.findByUsuario(username);
    }

    public List<Task> obtenerTareasPendientes(String username) {
        return repository.findPendientes(username);
    }

    public List<Task> obtenerTareasCompletadas(String username) {
        return repository.findCompletadas(username);
    }

    public List<Task> obtenerTareasPorPrioridad(String username, String prioridad) {
        return repository.findByPrioridad(username, prioridad);
    }

    public Optional<Task> buscarPorId(String id) {
        return repository.findById(id);
    }
}