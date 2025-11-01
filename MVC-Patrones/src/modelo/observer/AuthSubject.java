/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import modelo.entity.User;

/**
 *
 * @author samue
 */
public class AuthSubject {

    private static AuthSubject instance;
    private final List<AuthObserver> observers;
    private String currentUserId;
    private String currentUsername;

    private AuthSubject() {
        this.observers = new CopyOnWriteArrayList<>();
    }

    public static synchronized AuthSubject getInstance() {
        if (instance == null) {
            instance = new AuthSubject();
        }
        return instance;
    }

    public void addObserver(AuthObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(AuthObserver observer) {
        observers.remove(observer);
    }

    public void notifyLogin(User user) {
        this.currentUserId = user.getId();
        this.currentUsername = user.getNombreUsuario();

        for (AuthObserver observer : observers) {
            observer.onLoginSuccess(user);
        }
    }

    public void notifyRegister(User user) {
        this.currentUserId = user.getId();
        this.currentUsername = user.getNombreUsuario();

        for (AuthObserver observer : observers) {
            observer.onRegisterSuccess(user);
        }
    }

    public void notifyLogout(User user) {
        String userId = this.currentUserId;
        this.currentUserId = null;
        this.currentUsername = null;

        for (AuthObserver observer : observers) {
            observer.onLogout(user);
        }
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isAuthenticated() {
        return currentUserId != null;
    }
}
