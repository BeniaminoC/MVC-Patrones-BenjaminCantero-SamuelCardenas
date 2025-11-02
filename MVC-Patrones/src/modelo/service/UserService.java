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
 * Servicio encargado de la gestión y autenticación de usuarios dentro del
 * sistema.
 * <p>
 * La clase <code>UserService</code> funciona como capa de negocio entre la
 * interfaz de usuario (controladores o vistas) y el repositorio
 * {@link UserRepository}, manejando las validaciones, registro, autenticación y
 * búsqueda de usuarios.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 * <li>Registrar nuevos usuarios tras validar sus datos.</li>
 * <li>Verificar credenciales de acceso (autenticación).</li>
 * <li>Aplicar reglas de formato para nombre de usuario, correo y
 * contraseña.</li>
 * <li>Evitar duplicación de cuentas por nombre o email.</li>
 * </ul>
 *
 * @author Samuel
 */
public class UserService {

    /**
     * Repositorio encargado del almacenamiento y consulta de usuarios.
     */
    private final UserRepository repository;

    /**
     * Patrón de validación para correos electrónicos.
     */
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Crea una nueva instancia del servicio de usuarios y obtiene la instancia
     * única del {@link UserRepository}.
     */
    public UserService() {
        this.repository = UserRepository.getInstance();
    }

    // ===============================================================
    // REGISTRO Y VALIDACIONES
    // ===============================================================
    /**
     * Registra un nuevo usuario en el sistema tras validar sus datos.
     * <p>
     * Se verifican los siguientes criterios:
     * </p>
     * <ul>
     * <li>El nombre de usuario no debe estar vacío ni exceder 20
     * caracteres.</li>
     * <li>El correo debe tener un formato válido y no estar ya registrado.</li>
     * <li>La contraseña debe tener entre 6 y 50 caracteres y coincidir con la
     * confirmación.</li>
     * <li>No debe existir un usuario con el mismo nombre.</li>
     * </ul>
     *
     * @param username Nombre único del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña elegida.
     * @param confirmPassword Confirmación de la contraseña.
     * @return Objeto {@link User} recién creado y persistido.
     * @throws ValidationException Si algún dato de entrada no cumple los
     * criterios de validación.
     * @throws ExistingUserException Si ya existe un usuario con el mismo
     * nombre.
     */
    public User registrarUsuario(String username, String email, String password, String confirmPassword)
            throws ValidationException, ExistingUserException {

        // Validaciones de campos
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

    // ===============================================================
    // AUTENTICACIÓN
    // ===============================================================
    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     * <p>
     * Si el usuario no existe o la contraseña es incorrecta, se lanza una
     * excepción de autenticación.
     * </p>
     *
     * @param username Nombre del usuario.
     * @param password Contraseña a verificar.
     * @return El objeto {@link User} autenticado.
     * @throws AuthenticationException Si las credenciales son inválidas.
     */
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

    // ===============================================================
    // VALIDACIONES INTERNAS
    // ===============================================================
    /**
     * Valida el formato y longitud del nombre de usuario.
     *
     * @param username Nombre de usuario a validar.
     * @throws ValidationException Si el nombre es nulo, vacío, demasiado
     * corto/largo o contiene caracteres inválidos.
     */
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

    /**
     * Valida el formato del correo electrónico.
     *
     * @param email Correo electrónico a validar.
     * @throws ValidationException Si el correo está vacío o no cumple el patrón
     * de formato.
     */
    private void validarEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("El formato del email no es válido");
        }
    }

    /**
     * Valida las reglas de longitud y coincidencia de la contraseña.
     *
     * @param password Contraseña principal.
     * @param confirmPassword Confirmación de la contraseña.
     * @throws ValidationException Si las contraseñas no cumplen los criterios o
     * no coinciden.
     */
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

    // ===============================================================
    // CONSULTAS
    // ===============================================================
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Un {@link Optional} que contiene el usuario si fue encontrado, o
     * vacío si no existe.
     */
    public Optional<User> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}
