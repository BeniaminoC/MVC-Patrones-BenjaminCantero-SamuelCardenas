/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo.service;

import java.util.Optional;
import java.util.regex.Pattern;

import modelo.entity.User;
import modelo.exception.*;
import modelo.repository.UserRepository;

/**
 * Servicio de usuarios con autenticación
 */
public class UserService {

    private final UserRepository repository;
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserService() {
        this.repository = UserRepository.getInstance();
    }

    public User registrarUsuario(String username, String email, String password, String confirmPassword) 
            throws ValidationException, ExistingUserException {
        
        // Validaciones
        validarUsername(username);
        validarEmail(email);
        validarPassword(password, confirmPassword);

        // Verificar duplicados
        if (repository.findByUsername(username).isPresent()) {
            throw new ExistingUserException(username);
        }

        if (repository.findByEmail(email).isPresent()) {
            throw new ValidationException("El email ya está registrado");
        }

        // Crear usuario
        User user = new User(username, email, password);
        repository.save(user);
        return user;
    }

    public User autenticarUsuario(String username, String password) 
            throws AuthenticationException {
        
        Optional<User> userOpt = repository.findByUsername(username);
        
        if (!userOpt.isPresent()) {
            throw new AuthenticationException();
        }

        User user = userOpt.get();
        if (!user.verificarPassword(password)) {
            throw new AuthenticationException();
        }

        return user;
    }

    private void validarUsername(String username) throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("El nombre de usuario no puede estar vacío");
        }
        if (username.length() < 3) {
            throw new ValidationException("El nombre de usuario debe tener al menos 3 caracteres");
        }
        if (username.length() > 20) {
            throw new ValidationException("El nombre de usuario no puede exceder 20 caracteres");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new ValidationException("El nombre de usuario solo puede contener letras, números y guiones bajos");
        }
    }

    private void validarEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("El formato del email no es válido");
        }
    }

    private void validarPassword(String password, String confirmPassword) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("La contraseña no puede estar vacía");
        }
        if (password.length() < 6) {
            throw new ValidationException("La contraseña debe tener al menos 6 caracteres");
        }
        if (password.length() > 50) {
            throw new ValidationException("La contraseña no puede exceder 50 caracteres");
        }
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Las contraseñas no coinciden");
        }
    }

    public Optional<User> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}
