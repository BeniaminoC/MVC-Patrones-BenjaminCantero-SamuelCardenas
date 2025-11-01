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
 *
 * @author samue
 */
public class FileManager {

    private static final String DATA_DIR = "data";
    private static final String TASKS_FILE = "tasks.txt";
    private static final String USERS_FILE = "users.txt";

    private final ReadWriteLock taskLock = new ReentrantReadWriteLock();
    private final ReadWriteLock userLock = new ReentrantReadWriteLock();

    public FileManager() {
        initializeDataDirectory();
    }

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

    // ========== TAREAS ==========
    public List<Task> loadTasks() {
        taskLock.readLock().lock();
        try {
            return loadEntities(TASKS_FILE, Task::fromString);
        } finally {
            taskLock.readLock().unlock();
        }
    }

    public void saveTasks(List<Task> tareas) {
        taskLock.writeLock().lock();
        try {
            saveEntities(TASKS_FILE, tareas);
        } finally {
            taskLock.writeLock().unlock();
        }
    }

    // ========== USUARIOS ==========
    public List<User> loadUsers() {
        userLock.readLock().lock();
        try {
            return loadEntities(USERS_FILE, User::fromString);
        } finally {
            userLock.readLock().unlock();
        }
    }

    public void saveUsers(List<User> usuarios) {
        userLock.writeLock().lock();
        try {
            saveEntities(USERS_FILE, usuarios);
        } finally {
            userLock.writeLock().unlock();
        }
    }

    // ========== MÉTODOS GENÉRICOS ==========
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

    // Backup
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
