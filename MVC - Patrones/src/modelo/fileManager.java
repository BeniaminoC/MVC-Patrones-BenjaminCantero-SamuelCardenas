/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author samue
 */
public class fileManager {
   private static final String FILE_PATH = "data/tasks.txt"; // Ruta relativa

    public List<task> loadTasks() {
        List<task> tareas = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Crear archivo si no existe
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // crea /data si no existe
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    tareas.add(task.fromString(linea));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer tareas: " + e.getMessage());
        }
        return tareas;
    }

    public void saveTasks(List<task> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (task t : tareas) {
                writer.write(t.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar tareas: " + e.getMessage());
        }
    }
}
