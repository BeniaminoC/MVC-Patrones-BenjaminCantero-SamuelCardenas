/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BENJAMIN
 */
public class SubjectLog{
    private static SubjectLog instance;
    private List<ObserverLog> observers;
    private String currentUser;
    
    private SubjectLog() {
        this.observers = new ArrayList<>();
    }
    
    // Singleton para tener una única instancia
    public static SubjectLog getInstance() {
        if (instance == null) {
            instance = new SubjectLog();
        }
        return instance;
    }
    public void agregarObservador(ObserverLog o){
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }
    public void eliminarObservador(ObserverLog o){
        observers.remove(o);
    }
    public void notificarInicioSesion(String userid){
        this.currentUser = userid;
        for (ObserverLog observer : observers) {
            observer.inicioSesion(userid);
        }
    }
    
    public void notificarRegistroExitoso(String userid){
        this.currentUser = userid;
        for (ObserverLog observer : observers) {
            observer.registroExitoso(userid);
        }
    }
    
    public String getCurrentUser() {
        return currentUser;
    }
    
}
