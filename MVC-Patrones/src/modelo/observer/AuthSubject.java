/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import modelo.entity.User;

/**
 * Clase que implementa el patrón de diseño <b>Observer</b> para gestionar
 * eventos relacionados con la autenticación de usuarios.
 * <p>
 * {@code AuthSubject} actúa como sujeto observable, notificando a todos los
 * observadores registrados cuando ocurren eventos como inicio de sesión,
 * registro o cierre de sesión.
 * </p>
 *
 * <p>
 * La clase sigue el patrón <b>Singleton</b>, garantizando una única instancia
 * global accesible mediante {@link #getInstance()}.
 * </p>
 *
 * @author samue
 */
public class AuthSubject {

    /**
     * Instancia única del sujeto de autenticación.
     */
    private static AuthSubject instance;

    /**
     * Lista de observadores suscritos al sujeto.
     */
    private final List<AuthObserver> observers;

    /**
     * ID del usuario actualmente autenticado.
     */
    private String currentUserId;

    /**
     * Nombre de usuario actualmente autenticado.
     */
    private String currentUsername;

    /**
     * Constructor privado para el patrón Singleton.
     * <p>
     * Inicializa la lista de observadores con una estructura segura para
     * entornos concurrentes ({@link CopyOnWriteArrayList}).
     * </p>
     */
    private AuthSubject() {
        this.observers = new CopyOnWriteArrayList<>();
    }

    /**
     * Devuelve la instancia única del sujeto de autenticación.
     *
     * @return instancia única de {@code AuthSubject}
     */
    public static synchronized AuthSubject getInstance() {
        if (instance == null) {
            instance = new AuthSubject();
        }
        return instance;
    }

    /**
     * Registra un nuevo observador para recibir notificaciones de
     * autenticación. Si el observador ya está registrado, no se añade
     * nuevamente.
     *
     * @param observer observador que desea recibir eventos
     */
    public void addObserver(AuthObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Elimina un observador de la lista de notificaciones.
     *
     * @param observer observador a eliminar
     */
    public void removeObserver(AuthObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores que un usuario ha iniciado sesión
     * exitosamente.
     *
     * @param user usuario autenticado
     */
    public void notifyLogin(User user) {
        this.currentUserId = user.getId();
        this.currentUsername = user.getNombreUsuario();

        for (AuthObserver observer : observers) {
            observer.onLoginSuccess(user);
        }
    }

    /**
     * Notifica a todos los observadores que un usuario se ha registrado
     * exitosamente.
     *
     * @param user usuario recién registrado
     */
    public void notifyRegister(User user) {
        this.currentUserId = user.getId();
        this.currentUsername = user.getNombreUsuario();

        for (AuthObserver observer : observers) {
            observer.onRegisterSuccess(user);
        }
    }

    /**
     * Notifica a los observadores que un usuario ha cerrado sesión.
     * <p>
     * Limpia la información del usuario actualmente autenticado.
     * </p>
     *
     * @param user usuario que cerró sesión
     */
    public void notifyLogout(User user) {
        this.currentUserId = null;
        this.currentUsername = null;

        for (AuthObserver observer : observers) {
            observer.onLogout(user);
        }
    }

    /**
     * Obtiene el identificador del usuario actualmente autenticado.
     *
     * @return ID del usuario autenticado o {@code null} si no hay sesión activa
     */
    public String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Devuelve el nombre del usuario actualmente autenticado.
     *
     * @return nombre de usuario o {@code null} si no hay sesión activa
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Indica si hay un usuario autenticado en el sistema.
     *
     * @return {@code true} si existe un usuario autenticado, {@code false} en
     * caso contrario
     */
    public boolean isAuthenticated() {
        return currentUserId != null;
    }
}
