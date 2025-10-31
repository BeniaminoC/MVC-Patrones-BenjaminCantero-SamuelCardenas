/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import modelo.entity.User;

/**
 * Observer para eventos de autenticación
 */
public interface AuthObserver {
    void onLoginSuccess(User user);
    void onRegisterSuccess(User user);
    void onLogout(User user);
}