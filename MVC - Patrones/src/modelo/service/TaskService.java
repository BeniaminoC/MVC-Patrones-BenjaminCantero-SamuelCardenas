/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.service;

import modelo.entity.Task;
import modelo.exception.ExistingTaskException;
import modelo.exception.ModelException;
import modelo.repository.TaskRepository;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = TaskRepository.getInstance();
    }

    public void crearTarea(String titulo, String descripcion) throws ExistingTaskException, ModelException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ModelException("El título no puede estar vacío");
        }
        Optional<Task> existingTask = taskRepository.findByTitulo(titulo);
        if (existingTask.isPresent()) {
            throw new ExistingTaskException(titulo);
        }
        Task task = new Task(titulo, descripcion);
        taskRepository.save(task);
    }

    public List<Task> obtenerTodasLasTareas() {
        return taskRepository.findAll();
    }

    public List<Task> obtenerTareasPendientes() {
        return taskRepository.findPendientes();
    }

    public List<Task> obtenerTareasCompletadas() {
        return taskRepository.findCompletadas();
    }

    public List<Task> obtenerTareasPorUsuario(String username) {
        return taskRepository.findByUsuario(username);
    }

    public void eliminarTarea(Task task) {
        taskRepository.delete(task);
    }

    public void actualizarTarea(Task task) {
        // Como la tarea ya está en la lista, solo necesitamos actualizar el archivo
        taskRepository.update();
    }

    public void marcarTareaCompletada(Task task) {
        task.setCompletada(true);
        taskRepository.update();
    }
}