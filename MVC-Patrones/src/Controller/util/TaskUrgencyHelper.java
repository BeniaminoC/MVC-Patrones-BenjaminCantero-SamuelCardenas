/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author samue
 */
public class TaskUrgencyHelper {

    public static class UrgencyInfo {

        private final String text;
        private final String icon;
        private final String color;
        private final double progress;
        private final UrgencyLevel level;

        public UrgencyInfo(String text, String icon, String color, double progress, UrgencyLevel level) {
            this.text = text;
            this.icon = icon;
            this.color = color;
            this.progress = progress;
            this.level = level;
        }

        public String getText() {
            return text;
        }

        public String getIcon() {
            return icon;
        }

        public String getColor() {
            return color;
        }

        public double getProgress() {
            return progress;
        }

        public UrgencyLevel getLevel() {
            return level;
        }

        public String getFullText() {
            return icon + " " + text;
        }
    }

    public enum UrgencyLevel {
        OVERDUE, // Vencida
        TODAY, // Vence hoy
        TOMORROW, // Vence mañana
        CRITICAL, // 2-3 días
        WARNING, // 4-7 días
        NORMAL, // Más de 7 días
        NO_DEADLINE   // Sin fecha límite
    }

    // Calcula la información de urgencia para una fecha límite
    public static UrgencyInfo getUrgencyInfo(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return new UrgencyInfo(
                    "Sin fecha límite",
                    "📋",
                    "#6b7280",
                    0.0,
                    UrgencyLevel.NO_DEADLINE
            );
        }

        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);

        if (daysUntil < 0) {
            long daysOverdue = Math.abs(daysUntil);
            String text = "Vencida hace " + daysOverdue + " día" + (daysOverdue == 1 ? "" : "s");
            return new UrgencyInfo(text, "⚠️", "#dc2626", 1.0, UrgencyLevel.OVERDUE);

        } else if (daysUntil == 0) {
            return new UrgencyInfo("Vence HOY", "⏰", "#f59e0b", 1.0, UrgencyLevel.TODAY);

        } else if (daysUntil == 1) {
            return new UrgencyInfo("Vence MAÑANA", "⏰", "#f59e0b", 0.9, UrgencyLevel.TOMORROW);

        } else if (daysUntil <= 3) {
            String text = "Faltan " + daysUntil + " días";
            double progress = 1.0 - (daysUntil / 7.0);
            return new UrgencyInfo(text, "⏳", "#f59e0b", progress, UrgencyLevel.CRITICAL);

        } else if (daysUntil <= 7) {
            String text = "Faltan " + daysUntil + " días";
            double progress = 1.0 - (daysUntil / 7.0);
            return new UrgencyInfo(text, "📊", "#3b82f6", progress, UrgencyLevel.WARNING);

        } else {
            String text = "Faltan " + daysUntil + " días";
            return new UrgencyInfo(text, "✓", "#10b981", 0.0, UrgencyLevel.NORMAL);
        }
    }

    // Obtiene el número de días hasta la fecha límite
    public static long getDaysUntil(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
    }

    // Verifica si una tarea está vencida
    public static boolean isOverdue(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return false;
        }
        return LocalDate.now().isAfter(fechaLimite);
    }

    // Verifica si una tarea está próxima a vencer (3 días o menos)
    public static boolean isExpiringSoon(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return false;
        }
        long days = getDaysUntil(fechaLimite);
        return days >= 0 && days <= 3;
    }
}
