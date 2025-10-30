/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.persistence;

import modelo.entity.Task;
import modelo.entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileManager {

    private static final String TASKS_FILE_PATH = "data/tasks.txt";
    private static final String USERS_FILE_PATH = "data/users.txt"; // Nueva ruta para usuarios

    /**
     * Carga las tareas desde el archivo.
     */
    public List<Task> loadTasks() {
        return loadEntities(TASKS_FILE_PATH, line -> Task.fromString(line));
    }

    /**
     * Guarda las tareas en el archivo.
     */
    public void saveTasks(List<Task> tareas) {
        saveEntities(TASKS_FILE_PATH, tareas);
    }

    /**
     * Carga los usuarios desde el archivo.
     */
    public List<User> loadUsers() {
        return loadEntities(USERS_FILE_PATH, line -> User.fromString(line));
    }

    /**
     * Guarda los usuarios en el archivo.
     */
    public void saveUsers(List<User> usuarios) {
        saveEntities(USERS_FILE_PATH, usuarios);
    }

    /**
     * Método genérico para cargar entidades desde un archivo.
     */
    private <T> List<T> loadEntities(String filePath, Function<String, T> parser) {
        List<T> entities = new ArrayList<>();
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        T entity = parser.apply(linea);
                        if (entity != null) {
                            entities.add(entity);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo " + filePath + ": " + e.getMessage());
        }

        return entities;
    }

    /**
     * Método genérico para guardar entidades en un archivo.
     */
    private <T> void saveEntities(String filePath, List<T> entities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : entities) {
                writer.write(entity.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("❌ Error al guardar en el archivo " + filePath + ": " + e.getMessage());
        }
    }
}