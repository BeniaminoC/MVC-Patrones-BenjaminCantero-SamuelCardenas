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
 * Servicio que gestiona la lógica de negocio relacionada con las tareas.
 * <p>
 * La clase <code>TaskService</code> actúa como una capa intermedia entre la
 * capa de presentación (controladores o vistas) y la capa de persistencia
 * ({@link TaskRepository}). Se encarga de validar los datos de entrada, aplicar
 * reglas de negocio y coordinar las operaciones de creación, actualización,
 * eliminación y consulta de tareas.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 * <li>Validar los datos de las tareas antes de persistirlos.</li>
 * <li>Prevenir duplicados según título y usuario asignado.</li>
 * <li>Delegar las operaciones de almacenamiento al repositorio.</li>
 * <li>Proporcionar consultas específicas de tareas por usuario, prioridad o
 * estado.</li>
 * </ul>
 *
 * @author Samuel
 */
public class TaskService {

    /**
     * Repositorio de persistencia de tareas.
     */
    private final TaskRepository repository;

    /**
     * Crea una nueva instancia del servicio de tareas y obtiene la instancia
     * única del {@link TaskRepository}.
     */
    public TaskService() {
        this.repository = TaskRepository.getInstance();
    }

    // ===============================================================
    // CRUD Y VALIDACIÓN DE NEGOCIO
    // ===============================================================
    /**
     * Crea una nueva tarea tras aplicar validaciones de negocio.
     * <p>
     * Se valida que el título no esté vacío ni exceda los 100 caracteres y que
     * no exista una tarea duplicada con el mismo título y usuario asignado.
     * </p>
     *
     * @param titulo Título de la tarea.
     * @param descripcion Descripción detallada de la tarea.
     * @param fechaLimite Fecha límite de finalización (puede ser {@code null}).
     * @param prioridad Nivel de prioridad ("ALTA", "MEDIA", "BAJA", etc.).
     * @param usuarioAsignado Nombre de usuario asignado a la tarea.
     * @return Objeto {@link Task} recién creado y persistido.
     * @throws ValidationException Si los datos de la tarea no son válidos.
     * @throws ExistingTaskException Si ya existe una tarea con el mismo título
     * para el mismo usuario.
     */
    public Task crearTarea(String titulo, String descripcion,
            LocalDate fechaLimite, String prioridad,
            String usuarioAsignado)
            throws ValidationException, ExistingTaskException {

        // Validaciones básicas
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }

        if (titulo.length() > 100) {
            throw new ValidationException("El título no puede exceder 100 caracteres");
        }

        // Verificar duplicados por título y usuario
        Optional<Task> existing = repository.findByTitulo(titulo);
        if (existing.isPresent()
                && usuarioAsignado.equals(existing.get().getUsuarioAsignado())) {
            throw new ExistingTaskException(titulo);
        }

        // Crear y guardar tarea
        Task task = new Task(titulo, descripcion, fechaLimite, prioridad);
        task.setUsuarioAsignado(usuarioAsignado);
        repository.save(task);
        return task;
    }

    /**
     * Actualiza los datos de una tarea existente tras validar su título.
     *
     * @param task Objeto {@link Task} a actualizar.
     * @throws ValidationException Si el título es nulo o vacío.
     */
    public void actualizarTarea(Task task) throws ValidationException {
        if (task.getTitulo() == null || task.getTitulo().trim().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }
        repository.update(task);
    }

    /**
     * Elimina una tarea del sistema.
     *
     * @param task Objeto {@link Task} a eliminar.
     */
    public void eliminarTarea(Task task) {
        repository.delete(task);
    }

    /**
     * Marca una tarea como completada o pendiente.
     *
     * @param task Objeto {@link Task} a modificar.
     * @param completada Valor booleano que indica si la tarea está completada.
     */
    public void marcarCompletada(Task task, boolean completada) {
        task.setCompletada(completada);
        repository.update(task);
    }

    // ===============================================================
    // CONSULTAS Y FILTROS DE TAREAS
    // ===============================================================
    /**
     * Obtiene todas las tareas asociadas a un usuario.
     *
     * @param username Nombre del usuario.
     * @return Lista de tareas del usuario.
     */
    public List<Task> obtenerTareasUsuario(String username) {
        return repository.findByUsuario(username);
    }

    /**
     * Obtiene todas las tareas pendientes (no completadas) de un usuario.
     *
     * @param username Nombre del usuario.
     * @return Lista de tareas pendientes.
     */
    public List<Task> obtenerTareasPendientes(String username) {
        return repository.findPendientes(username);
    }

    /**
     * Obtiene todas las tareas completadas de un usuario.
     *
     * @param username Nombre del usuario.
     * @return Lista de tareas completadas.
     */
    public List<Task> obtenerTareasCompletadas(String username) {
        return repository.findCompletadas(username);
    }

    /**
     * Obtiene las tareas de un usuario filtradas por prioridad.
     *
     * @param username Nombre del usuario.
     * @param prioridad Nivel de prioridad ("ALTA", "MEDIA", "BAJA").
     * @return Lista de tareas filtradas por prioridad.
     */
    public List<Task> obtenerTareasPorPrioridad(String username, String prioridad) {
        return repository.findByPrioridad(username, prioridad);
    }

    /**
     * Busca una tarea por su identificador único.
     *
     * @param id Identificador de la tarea.
     * @return {@link Optional} que contiene la tarea si fue encontrada.
     */
    public Optional<Task> buscarPorId(String id) {
        return repository.findById(id);
    }
}
