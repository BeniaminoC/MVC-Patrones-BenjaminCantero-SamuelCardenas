/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repository;

import modelo.entity.User;
import modelo.persistence.FileManager;
import java.util.*;

/**
 * Repositorio de usuarios
 */
public class UserRepository {

    private static UserRepository instance;
    private final FileManager fileManager;
    private final List<User> cache;

    private UserRepository() {
        this.fileManager = new FileManager();
        this.cache = Collections.synchronizedList(new ArrayList<>());
        loadCache();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private void loadCache() {
        cache.clear();
        cache.addAll(fileManager.loadUsers());
    }

    public synchronized void save(User user) {
        cache.add(user);
        persist();
    }

    public synchronized void update(User user) {
        persist();
    }

    public synchronized void delete(User user) {
        cache.removeIf(u -> u.getId().equals(user.getId()));
        persist();
    }

    public List<User> findAll() {
        synchronized (cache) {
            return new ArrayList<>(cache);
        }
    }

    public Optional<User> findById(String id) {
        synchronized (cache) {
            return cache.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
        }
    }

    public Optional<User> findByUsername(String username) {
        synchronized (cache) {
            return cache.stream()
                .filter(user -> user.getNombreUsuario().equalsIgnoreCase(username))
                .findFirst();
        }
    }

    public Optional<User> findByEmail(String email) {
        synchronized (cache) {
            return cache.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
        }
    }

    private void persist() {
        fileManager.createBackup("users.txt");
        fileManager.saveUsers(new ArrayList<>(cache));
    }

    public synchronized void refresh() {
        loadCache();
    }

    public synchronized void clearAll() {
        cache.clear();
        persist();
    }
}
