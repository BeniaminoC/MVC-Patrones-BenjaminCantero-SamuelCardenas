/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.persistence;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import modelo.entity.Task;
import modelo.entity.User;

/**
 * Clase encargada de la gestión de persistencia mediante archivos locales.
 * <p>
 * <strong>FileManager</strong> actúa como una capa de acceso a datos simple
 * (DAO) que almacena y recupera entidades de tipo {@link Task} y {@link User}
 * en archivos de texto plano. Utiliza bloqueos de lectura/escritura para
 * garantizar la seguridad en entornos concurrentes.
 * </p>
 *
 * <h3>Características principales:</h3>
 * <ul>
 * <li>Persistencia basada en archivos (.txt) dentro del directorio
 * <code>data/</code>.</li>
 * <li>Bloqueos independientes para tareas y usuarios mediante
 * {@link ReadWriteLock}.</li>
 * <li>Métodos genéricos reutilizables para carga y guardado de entidades.</li>
 * <li>Creación automática del directorio de datos y de archivos si no
 * existen.</li>
 * <li>Generación de copias de respaldo (.backup) bajo demanda.</li>
 * </ul>
 *
 * @author Samuel
 */
public class FileManager {

    /**
     * Carpeta base donde se almacenan los archivos de datos.
     */
    private static final String DATA_DIR = "data";

    /**
     * Archivo que contiene las tareas serializadas.
     */
    private static final String TASKS_FILE = "tasks.txt";

    /**
     * Archivo que contiene los usuarios serializados.
     */
    private static final String USERS_FILE = "users.txt";

    /**
     * Bloqueo de lectura/escritura para operaciones sobre tareas.
     */
    private final ReadWriteLock taskLock = new ReentrantReadWriteLock();

    /**
     * Bloqueo de lectura/escritura para operaciones sobre usuarios.
     */
    private final ReadWriteLock userLock = new ReentrantReadWriteLock();

    /**
     * Constructor que inicializa el gestor de archivos y garantiza la
     * existencia del directorio base de datos.
     */
    public FileManager() {
        initializeDataDirectory();
    }


    /**
     * Verifica y crea el directorio de almacenamiento <code>data/</code> en
     * caso de no existir.
     */
    private void initializeDataDirectory() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorio de datos: " + e.getMessage());
        }
    }


    /**
     * Carga la lista completa de tareas almacenadas desde el archivo
     * <code>tasks.txt</code>.
     *
     * @return Lista de objetos {@link Task} leídos del archivo.
     */
    public List<Task> loadTasks() {
        taskLock.readLock().lock();
        try {
            return loadEntities(TASKS_FILE, Task::fromString);
        } finally {
            taskLock.readLock().unlock();
        }
    }

    /**
     * Guarda en el archivo <code>tasks.txt</code> la lista de tareas
     * especificada, sobrescribiendo su contenido anterior.
     *
     * @param tareas Lista de tareas a persistir.
     */
    public void saveTasks(List<Task> tareas) {
        taskLock.writeLock().lock();
        try {
            saveEntities(TASKS_FILE, tareas);
        } finally {
            taskLock.writeLock().unlock();
        }
    }


    /**
     * Carga la lista completa de usuarios desde el archivo
     * <code>users.txt</code>.
     *
     * @return Lista de objetos {@link User} recuperados del archivo.
     */
    public List<User> loadUsers() {
        userLock.readLock().lock();
        try {
            return loadEntities(USERS_FILE, User::fromString);
        } finally {
            userLock.readLock().unlock();
        }
    }

    /**
     * Guarda en el archivo <code>users.txt</code> la lista de usuarios
     * proporcionada, reemplazando cualquier contenido previo.
     *
     * @param usuarios Lista de usuarios a guardar.
     */
    public void saveUsers(List<User> usuarios) {
        userLock.writeLock().lock();
        try {
            saveEntities(USERS_FILE, usuarios);
        } finally {
            userLock.writeLock().unlock();
        }
    }


    /**
     * Carga entidades genéricas desde un archivo de texto, aplicando una
     * función de parseo por línea.
     *
     * @param <T> Tipo de entidad a cargar.
     * @param filename Nombre del archivo a leer.
     * @param parser Función que transforma cada línea del archivo en una
     * instancia del tipo <code>T</code>.
     * @return Lista de entidades válidas encontradas en el archivo.
     */
    private <T> List<T> loadEntities(String filename, Function<String, T> parser) {
        List<T> entities = new ArrayList<>();
        Path filePath = Paths.get(DATA_DIR, filename);

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return entities;
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line != null && !line.trim().isEmpty()) {
                    T entity = parser.apply(line);
                    if (entity != null) {
                        entities.add(entity);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer " + filename + ": " + e.getMessage());
        }

        return entities;
    }

    /**
     * Guarda una lista de entidades genéricas en un archivo, sobrescribiendo su
     * contenido existente.
     *
     * @param <T> Tipo de entidad a guardar.
     * @param filename Nombre del archivo destino.
     * @param entities Lista de entidades a escribir.
     */
    private <T> void saveEntities(String filename, List<T> entities) {
        Path filePath = Paths.get(DATA_DIR, filename);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (T entity : entities) {
                writer.write(entity.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar " + filename + ": " + e.getMessage());
        }
    }


    /**
     * Crea una copia de respaldo (.backup) del archivo especificado dentro del
     * mismo directorio de datos.
     * <p>
     * Si el archivo original no existe, no se realiza ninguna acción.
     * </p>
     *
     * @param filename Nombre del archivo original del cual se desea generar una
     * copia de seguridad.
     */
    public void createBackup(String filename) {
        try {
            Path source = Paths.get(DATA_DIR, filename);
            if (Files.exists(source)) {
                Path backup = Paths.get(DATA_DIR, filename + ".backup");
                Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Error al crear backup: " + e.getMessage());
        }
    }
}
