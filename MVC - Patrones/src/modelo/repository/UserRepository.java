/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repository;

import modelo.entity.User;
import modelo.persistence.FileManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private static UserRepository instance;
    private final FileManager fileManager;
    private final List<User> usuarios;

    private UserRepository() {
        this.fileManager = new FileManager();
        this.usuarios = new ArrayList<>(fileManager.loadUsers());
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void save(User user) {
        usuarios.add(user);
        fileManager.saveUsers(usuarios);
    }

    public List<User> findAll() {
        return new ArrayList<>(usuarios);
    }

    public Optional<User> findByUsername(String username) {
        return usuarios.stream()
                .filter(user -> user.getNombreUsuario().equals(username))
                .findFirst();
    }

    public void delete(User user) {
        usuarios.remove(user);
        fileManager.saveUsers(usuarios);
    }

    public void update() {
        fileManager.saveUsers(usuarios);
    }

    public void clearAll() {
        usuarios.clear();
        fileManager.saveUsers(usuarios);
    }
}