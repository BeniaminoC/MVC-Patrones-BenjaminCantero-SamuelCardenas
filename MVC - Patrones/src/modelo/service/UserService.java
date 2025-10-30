/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.service;

import modelo.entity.User;
import modelo.exception.ExistingUserException;
import modelo.exception.ModelException;
import modelo.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public void registrarUsuario(String username, String password) throws ExistingUserException, ModelException {
        if (username == null || username.trim().isEmpty()) {
            throw new ModelException("El nombre de usuario no puede estar vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ModelException("La contraseña no puede estar vacía");
        }
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new ExistingUserException(username);
        }
        User user = new User(username, password);
        userRepository.save(user);
    }

    public boolean autenticarUsuario(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getContraUser().equals(password);
    }

    public List<User> obtenerTodosLosUsuarios() {
        return userRepository.findAll();
    }
}