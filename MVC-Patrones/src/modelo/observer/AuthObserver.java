/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import modelo.entity.User;

/**
 * Interfaz que define el contrato para observadores del sistema de autenticación.
 * <p>
 * {@code AuthObserver} forma parte del patrón de diseño <b>Observer</b>, 
 * permitiendo que los objetos que implementen esta interfaz sean notificados 
 * cuando ocurran eventos relacionados con la autenticación de usuarios.
 * </p>
 * 
 * <p>
 * Los observadores deben registrarse en {@link AuthSubject} mediante el método
 * {@link AuthSubject#addObserver(AuthObserver)} para recibir notificaciones.
 * </p>
 * 
 * <h3>Eventos soportados:</h3>
 * <ul>
 *   <li>Inicio de sesión exitoso ({@link #onLoginSuccess(User)})</li>
 *   <li>Registro exitoso ({@link #onRegisterSuccess(User)})</li>
 *   <li>Cierre de sesión ({@link #onLogout(User)})</li>
 * </ul>
 * 
 *
 * @author samue
 */
public interface AuthObserver {

    /**
     * Método invocado cuando un usuario inicia sesión exitosamente.
     * <p>
     * Este método es llamado por {@link AuthSubject#notifyLogin(User)} 
     * después de que el usuario ha sido autenticado correctamente.
     * </p>
     * 
     * <p>
     * Las implementaciones típicas de este método incluyen:
     * <ul>
     *   <li>Cargar las tareas del usuario</li>
     *   <li>Actualizar la interfaz con información del usuario</li>
     *   <li>Inicializar estadísticas y métricas</li>
     *   <li>Configurar preferencias del usuario</li>
     * </ul>
     * </p>
     *
     * @param user objeto con la información del usuario autenticado
     */
    void onLoginSuccess(User user);

    /**
     * Método invocado cuando un nuevo usuario se registra exitosamente.
     * <p>
     * Este método es llamado por {@link AuthSubject#notifyRegister(User)} 
     * después de que el usuario ha sido creado y guardado en el sistema.
     * </p>
     * 
     * <p>
     * Las implementaciones típicas de este método incluyen:
     * <ul>
     *   <li>Preparar la vista principal para un usuario nuevo</li>
     *   <li>Configurar ajustes iniciales</li>
     * </ul>
     * </p>
     *
     * @param user objeto con la información del usuario recién registrado
     */
    void onRegisterSuccess(User user);

    /**
     * Método invocado cuando un usuario cierra sesión.
     * <p>
     * Este método es llamado por {@link AuthSubject#notifyLogout(User)} 
     * cuando el usuario termina su sesión en el sistema.
     * </p>
     * 
     * <p>
     * Las implementaciones típicas de este método incluyen:
     * <ul>
     *   <li>Reiniciar la interfaz al estado inicial</li>
     *   <li>Guardar configuraciones pendientes</li>
     *   <li>Liberar recursos asociados a la sesión</li>
     * </ul>
     * </p>
     *
     * @param user objeto con la información del usuario que cerró sesión
     */
    void onLogout(User user);
}
