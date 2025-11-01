/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repository;

import java.util.*;
import java.util.stream.Collectors;

import modelo.entity.Task;
import modelo.persistence.FileManager;

/**
 *
 * @author samue
 */
public class TaskRepository {

    private static TaskRepository instance;
    private final FileManager fileManager;
    private final List<Task> cache;

    private TaskRepository() {
        this.fileManager = new FileManager();
        this.cache = Collections.synchronizedList(new ArrayList<>());
        loadCache();
    }

    public static synchronized TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    private void loadCache() {
        cache.clear();
        cache.addAll(fileManager.loadTasks());
    }

    public synchronized void save(Task task) {
        cache.add(task);
        persist();
    }

    // Actualiza una tarea existente en el caché
    public synchronized void update(Task task) {
        Optional<Task> optionalTask = findById(task.getId());

        if (!optionalTask.isPresent()) {
            throw new IllegalArgumentException("No se puede actualizar: tarea con ID " + task.getId() + " no encontrada");
        }

        Task existingTask = optionalTask.get();
        existingTask.setTitulo(task.getTitulo());
        existingTask.setDescripcion(task.getDescripcion());
        existingTask.setFechaLimite(task.getFechaLimite());
        existingTask.setPrioridad(task.getPrioridad());
        existingTask.setCompletada(task.isCompletada());

        persist();
    }

    public synchronized void delete(Task task) {
        cache.removeIf(t -> t.getId().equals(task.getId()));
        persist();
    }

    public List<Task> findAll() {
        synchronized (cache) {
            return new ArrayList<>(cache);
        }
    }

    public Optional<Task> findById(String id) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> task.getId().equals(id))
                    .findFirst();
        }
    }

    public Optional<Task> findByTitulo(String titulo) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> task.getTitulo().equalsIgnoreCase(titulo))
                    .findFirst();
        }
    }

    public List<Task> findByUsuario(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .collect(Collectors.toList());
        }
    }

    public List<Task> findPendientes(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(task -> !task.isCompletada())
                    .collect(Collectors.toList());
        }
    }

    public List<Task> findCompletadas(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(Task::isCompletada)
                    .collect(Collectors.toList());
        }
    }

    public List<Task> findByPrioridad(String username, String prioridad) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(task -> prioridad.equalsIgnoreCase(task.getPrioridad()))
                    .collect(Collectors.toList());
        }
    }

    private void persist() {
        fileManager.createBackup("tasks.txt");
        fileManager.saveTasks(new ArrayList<>(cache));
    }

    public synchronized void refresh() {
        loadCache();
    }

    public synchronized void clearAll() {
        cache.clear();
        persist();
    }
}
