/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Subject para notificar eventos de autenticación
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

    public void notifyLogin(String userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
        
        for (AuthObserver observer : observers) {
            observer.onLoginSuccess(userId, username);
        }
    }

    public void notifyRegister(String userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
        
        for (AuthObserver observer : observers) {
            observer.onRegisterSuccess(userId, username);
        }
    }

    public void notifyLogout() {
        String userId = this.currentUserId;
        this.currentUserId = null;
        this.currentUsername = null;
        
        for (AuthObserver observer : observers) {
            observer.onLogout(userId);
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