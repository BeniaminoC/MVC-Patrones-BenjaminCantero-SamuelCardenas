/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.helper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author samue
 */
public class TaskStatusHelper {

    public static long calcularDiasRestantes(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
    }

    public static String obtenerEtiquetaEstado(long diasRestantes) {
        if (diasRestantes < 0) {
            return "[VENCIDA]";
        }
        if (diasRestantes == 0) {
            return "[HOY]";
        }
        if (diasRestantes <= 3) {
            return "(" + diasRestantes + " días)";
        }
        return "";
    }

    public static String obtenerIcono(long diasRestantes) {
        if (diasRestantes < 0) {
            return "⚠️";
        }
        if (diasRestantes == 0) {
            return "⏰";
        }
        if (diasRestantes <= 3) {
            return "⏳";
        }
        return "";
    }

    public static String obtenerClaseCSS(long diasRestantes) {
        if (diasRestantes < 0) {
            return "task-overdue";
        }
        if (diasRestantes <= 3) {
            return "task-expiring-soon";
        }
        return "";
    }
}
