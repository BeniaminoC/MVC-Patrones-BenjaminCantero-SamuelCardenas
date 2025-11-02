/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repository;

import java.util.*;
import modelo.entity.User;
import modelo.persistence.FileManager;

/**
 * Repositorio encargado de gestionar la persistencia y el acceso a los datos de
 * usuarios del sistema.
 * <p>
 * Implementa el patrón Singleton para garantizar que exista una sola instancia
 * de {@code UserRepository} durante toda la ejecución del programa.
 * </p>
 *
 * <p>
 * Este repositorio mantiene una caché sincronizada de objetos {@link User}
 * cargados desde el almacenamiento en archivos, administrado por la clase
 * {@link FileManager}.
 * </p>
 *
 * @author samue
 */
public class UserRepository {

    /**
     * Instancia única del repositorio (Singleton).
     */
    private static UserRepository instance;

    /**
     * Manejador de archivos responsable de la lectura y escritura de usuarios.
     */
    private final FileManager fileManager;

    /**
     * Caché sincronizada de usuarios en memoria.
     */
    private final List<User> cache;

    /**
     * Constructor privado. Carga en memoria los usuarios almacenados en disco.
     */
    private UserRepository() {
        this.fileManager = new FileManager();
        this.cache = Collections.synchronizedList(new ArrayList<>());
        loadCache();
    }

    /**
     * Devuelve la instancia única del repositorio de usuarios.
     *
     * @return instancia única de {@code UserRepository}
     */
    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    /**
     * Carga los usuarios desde el archivo persistente hacia la caché en
     * memoria.
     */
    private void loadCache() {
        cache.clear();
        cache.addAll(fileManager.loadUsers());
    }

    /**
     * Guarda un nuevo usuario en el repositorio y actualiza el almacenamiento.
     *
     * @param user el usuario a registrar
     */
    public synchronized void save(User user) {
        cache.add(user);
        persist();
    }

    /**
     * Actualiza la información de un usuario existente en el repositorio.
     * <p>
     * En esta implementación, la actualización simplemente reescribe el archivo
     * completo con el estado actual de la caché.
     * </p>
     *
     * @param user usuario con los datos actualizados
     */
    public synchronized void update(User user) {
        persist();
    }

    /**
     * Elimina un usuario del repositorio y actualiza el almacenamiento.
     *
     * @param user el usuario a eliminar
     */
    public synchronized void delete(User user) {
        cache.removeIf(u -> u.getId().equals(user.getId()));
        persist();
    }

    /**
     * Devuelve una lista de todos los usuarios almacenados en el repositorio.
     *
     * @return lista de usuarios registrados
     */
    public List<User> findAll() {
        synchronized (cache) {
            return new ArrayList<>(cache);
        }
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id identificador del usuario
     * @return un {@link Optional} que contiene el usuario si se encuentra
     */
    public Optional<User> findById(String id) {
        synchronized (cache) {
            return cache.stream()
                    .filter(user -> user.getId().equals(id))
                    .findFirst();
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario a buscar
     * @return un {@link Optional} con el usuario si existe
     */
    public Optional<User> findByUsername(String username) {
        synchronized (cache) {
            return cache.stream()
                    .filter(user -> user.getNombreUsuario().equalsIgnoreCase(username))
                    .findFirst();
        }
    }

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email correo electrónico del usuario
     * @return un {@link Optional} con el usuario si se encuentra registrado
     */
    public Optional<User> findByEmail(String email) {
        synchronized (cache) {
            return cache.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }
    }

    /**
     * Persiste los usuarios en disco, creando primero una copia de seguridad.
     */
    private void persist() {
        fileManager.createBackup("users.txt");
        fileManager.saveUsers(new ArrayList<>(cache));
    }

    /**
     * Recarga la caché desde el archivo de almacenamiento.
     * <p>
     * Se recomienda llamar a este método cuando el archivo externo de usuarios
     * haya cambiado y sea necesario sincronizar el repositorio.
     * </p>
     */
    public synchronized void refresh() {
        loadCache();
    }

    /**
     * Elimina todos los usuarios del repositorio y limpia el archivo
     * persistente.
     * <p>
     * Este método debe usarse con precaución, ya que borra toda la información
     * de usuarios almacenada.
     * </p>
     */
    public synchronized void clearAll() {
        cache.clear();
        persist();
    }
}
