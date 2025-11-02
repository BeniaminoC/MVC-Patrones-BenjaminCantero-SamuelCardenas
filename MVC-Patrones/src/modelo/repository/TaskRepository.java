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
 * Repositorio encargado de la gestión y persistencia de objetos {@link Task}.
 * <p>
 * Implementa el patrón Singleton para garantizar una única instancia global del
 * repositorio durante la ejecución de la aplicación.
 * </p>
 *
 * <p>
 * Este repositorio mantiene una caché sincronizada en memoria con las tareas
 * cargadas desde el almacenamiento en archivos, administrado por la clase
 * {@link FileManager}.
 * </p>
 *
 * @author samue
 */
public class TaskRepository {

    /**
     * Instancia única del repositorio (Singleton).
     */
    private static TaskRepository instance;

    /**
     * Manejador de archivos responsable de la persistencia de datos.
     */
    private final FileManager fileManager;

    /**
     * Caché sincronizada de tareas almacenadas en memoria.
     */
    private final List<Task> cache;

    /**
     * Constructor privado. Carga las tareas almacenadas en disco al inicializar
     * la caché.
     */
    private TaskRepository() {
        this.fileManager = new FileManager();
        this.cache = Collections.synchronizedList(new ArrayList<>());
        loadCache();
    }

    /**
     * Devuelve la instancia única del repositorio.
     *
     * @return instancia única de {@code TaskRepository}
     */
    public static synchronized TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    /**
     * Carga en memoria todas las tareas almacenadas en el archivo persistente.
     */
    private void loadCache() {
        cache.clear();
        cache.addAll(fileManager.loadTasks());
    }

    /**
     * Guarda una nueva tarea en el repositorio.
     *
     * @param task la tarea a guardar
     */
    public synchronized void save(Task task) {
        cache.add(task);
        persist();
    }

    /**
     * Actualiza una tarea existente dentro del repositorio.
     *
     * @param task la tarea actualizada
     * @throws IllegalArgumentException si la tarea no existe en el repositorio
     */
    public synchronized void update(Task task) {
        Optional<Task> optionalTask = findById(task.getId());

        if (!optionalTask.isPresent()) {
            throw new IllegalArgumentException(
                    "No se puede actualizar: tarea con ID " + task.getId() + " no encontrada"
            );
        }

        Task existingTask = optionalTask.get();
        existingTask.setTitulo(task.getTitulo());
        existingTask.setDescripcion(task.getDescripcion());
        existingTask.setFechaLimite(task.getFechaLimite());
        existingTask.setPrioridad(task.getPrioridad());
        existingTask.setCompletada(task.isCompletada());

        persist();
    }

    /**
     * Elimina una tarea del repositorio.
     *
     * @param task la tarea a eliminar
     */
    public synchronized void delete(Task task) {
        cache.removeIf(t -> t.getId().equals(task.getId()));
        persist();
    }

    /**
     * Devuelve una lista con todas las tareas almacenadas.
     *
     * @return lista de todas las tareas
     */
    public List<Task> findAll() {
        synchronized (cache) {
            return new ArrayList<>(cache);
        }
    }

    /**
     * Busca una tarea por su identificador único.
     *
     * @param id identificador de la tarea
     * @return un {@link Optional} que contiene la tarea si existe
     */
    public Optional<Task> findById(String id) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> task.getId().equals(id))
                    .findFirst();
        }
    }

    /**
     * Busca una tarea por su título.
     *
     * @param titulo título de la tarea
     * @return un {@link Optional} con la tarea correspondiente, si existe
     */
    public Optional<Task> findByTitulo(String titulo) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> task.getTitulo().equalsIgnoreCase(titulo))
                    .findFirst();
        }
    }

    /**
     * Obtiene todas las tareas asignadas a un usuario específico.
     *
     * @param username nombre del usuario asignado
     * @return lista de tareas asociadas al usuario
     */
    public List<Task> findByUsuario(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Obtiene todas las tareas pendientes (no completadas) de un usuario.
     *
     * @param username nombre del usuario
     * @return lista de tareas pendientes del usuario
     */
    public List<Task> findPendientes(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(task -> !task.isCompletada())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Obtiene todas las tareas completadas de un usuario.
     *
     * @param username nombre del usuario
     * @return lista de tareas completadas del usuario
     */
    public List<Task> findCompletadas(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(Task::isCompletada)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Obtiene las tareas de un usuario filtradas por prioridad.
     *
     * @param username nombre del usuario
     * @param prioridad nivel de prioridad (por ejemplo, "Alta", "Media",
     * "Baja")
     * @return lista de tareas del usuario con la prioridad especificada
     */
    public List<Task> findByPrioridad(String username, String prioridad) {
        synchronized (cache) {
            return cache.stream()
                    .filter(task -> username.equals(task.getUsuarioAsignado()))
                    .filter(task -> prioridad.equalsIgnoreCase(task.getPrioridad()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Persiste en disco el contenido actual de la caché.
     * <p>
     * Antes de guardar, crea una copia de respaldo (backup) del archivo.</p>
     */
    private void persist() {
        fileManager.createBackup("tasks.txt");
        fileManager.saveTasks(new ArrayList<>(cache));
    }

    /**
     * Recarga la caché desde el almacenamiento persistente.
     */
    public synchronized void refresh() {
        loadCache();
    }

    /**
     * Elimina todas las tareas del repositorio y actualiza el almacenamiento.
     */
    public synchronized void clearAll() {
        cache.clear();
        persist();
    }
}
