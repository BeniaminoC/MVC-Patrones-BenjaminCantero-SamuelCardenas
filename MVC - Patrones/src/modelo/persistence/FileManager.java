/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.persistence;
/**
 *
 * @author samue
 */
import modelo.entity.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class FileManager {

    private static final String FILE_PATH = "data/tasks.txt"; // Ruta relativa

    /**
     * Carga todas las tareas desde el archivo.
     * Si el archivo no existe, lo crea vacío.
     */
    public List<Task> loadTasks() {
        List<Task> tareas = new ArrayList<>();
        File file = new File(FILE_PATH);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // crea /data si no existe
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    Task t = Task.fromString(linea);
                    if (t != null) {
                        tareas.add(t);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Error al leer tareas: " + e.getMessage());
        }

        return tareas;
    }

    /**
     * Guarda todas las tareas en el archivo, sobrescribiendo el contenido anterior.
     */
    public void saveTasks(List<Task> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task t : tareas) {
                writer.write(t.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("❌ Error al guardar tareas: " + e.getMessage());
        }
    }
}