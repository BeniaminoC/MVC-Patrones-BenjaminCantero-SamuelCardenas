/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

/**
 * Observer para eventos de autenticación
 */
public interface AuthObserver {
    void onLoginSuccess(String userId, String username);
    void onRegisterSuccess(String userId, String username);
    void onLogout(String userId);
}