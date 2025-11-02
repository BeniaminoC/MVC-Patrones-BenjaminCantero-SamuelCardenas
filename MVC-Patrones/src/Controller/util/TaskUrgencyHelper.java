/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Proporciona utilidades para evaluar el nivel de urgencia de una tarea según
 * su fecha límite.
 *
 * <p>
 * Esta clase permite obtener información visual y textual (color, ícono, texto
 * descriptivo y nivel de urgencia) que puede ser utilizada en la interfaz de
 * usuario para representar el estado de proximidad o vencimiento de una
 * tarea.</p>
 *
 * @author Samuel
 *
 */
public class TaskUrgencyHelper {

    /**
     * Contiene los datos visuales y textuales asociados a un nivel de urgencia.
     * <p>
     * Incluye texto descriptivo, ícono, color representativo, progreso visual y
     * el nivel de urgencia clasificado mediante la enumeración
     * {@link UrgencyLevel}.</p>
     */
    public static class UrgencyInfo {

        private final String text;
        private final String icon;
        private final String color;
        private final double progress;
        private final UrgencyLevel level;

        /**
         * Crea una nueva instancia de {@code UrgencyInfo}.
         *
         * @param text Descripción textual del nivel de urgencia (por ejemplo,
         * "Vence hoy").
         * @param icon Ícono representativo del estado (por ejemplo, "⚠️" o
         * "⏰").
         * @param color Código de color hexadecimal o nombre CSS asociado.
         * @param progress Progreso numérico entre 0 y 1 que indica cuán próxima
         * está la fecha límite.
         * @param level Nivel de urgencia definido por la enumeración
         * {@link UrgencyLevel}.
         */
        public UrgencyInfo(String text, String icon, String color, double progress, UrgencyLevel level) {
            this.text = text;
            this.icon = icon;
            this.color = color;
            this.progress = progress;
            this.level = level;
        }

        /**
         * Obtiene el texto descriptivo del nivel de urgencia.
         *
         * @return Texto representativo del estado de la tarea.
         */
        public String getText() {
            return text;
        }

        /**
         * Obtiene el ícono asociado al nivel de urgencia.
         *
         * @return Ícono visual (emoji o símbolo Unicode).
         */
        public String getIcon() {
            return icon;
        }

        /**
         * Obtiene el color asociado al nivel de urgencia.
         *
         * @return Código de color en formato hexadecimal o nombre CSS.
         */
        public String getColor() {
            return color;
        }

        /**
         * Obtiene el progreso visual relacionado con la proximidad de la fecha
         * límite.
         *
         * @return Valor numérico entre 0 y 1.
         */
        public double getProgress() {
            return progress;
        }

        /**
         * Obtiene el nivel de urgencia clasificado.
         *
         * @return Valor de {@link UrgencyLevel}.
         */
        public UrgencyLevel getLevel() {
            return level;
        }

        /**
         * Combina el ícono y el texto en una sola representación legible.
         *
         * @return Cadena compuesta por el ícono y el texto descriptivo.
         */
        public String getFullText() {
            return icon + " " + text;
        }
    }

    /**
     * Representa los diferentes niveles de urgencia que puede tener una tarea
     * según su fecha límite.
     */
    public enum UrgencyLevel {
        /**
         * La tarea ya está vencida.
         */
        OVERDUE,
        /**
         * La tarea vence el día de hoy.
         */
        TODAY,
        /**
         * La tarea vence mañana.
         */
        TOMORROW,
        /**
         * La tarea vence en los próximos 2 o 3 días.
         */
        CRITICAL,
        /**
         * La tarea vence en un rango de 4 a 7 días.
         */
        WARNING,
        /**
         * La tarea tiene más de 7 días antes de vencer.
         */
        NORMAL,
        /**
         * La tarea no tiene fecha límite asignada.
         */
        NO_DEADLINE
    }

    /**
     * Calcula la información de urgencia correspondiente a una fecha límite.
     *
     * @param fechaLimite Fecha límite de la tarea.
     * @return Objeto {@link UrgencyInfo} que describe el nivel de urgencia.
     */
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

    /**
     * Calcula el número de días restantes hasta la fecha límite especificada.
     *
     * @param fechaLimite Fecha límite de la tarea.
     * @return Número de días restantes. Si {@code fechaLimite} es nula,
     * devuelve {@link Long#MAX_VALUE}.
     */
    public static long getDaysUntil(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
    }

    /**
     * Verifica si la tarea está vencida con respecto a la fecha actual.
     *
     * @param fechaLimite Fecha límite de la tarea.
     * @return {@code true} si la tarea está vencida; de lo contrario,
     * {@code false}.
     */
    public static boolean isOverdue(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return false;
        }
        return LocalDate.now().isAfter(fechaLimite);
    }

    /**
     * Verifica si la tarea está próxima a vencer (3 días o menos).
     *
     * @param fechaLimite Fecha límite de la tarea.
     * @return {@code true} si faltan 3 días o menos para el vencimiento; de lo
     * contrario, {@code false}.
     */
    public static boolean isExpiringSoon(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            return false;
        }
        long days = getDaysUntil(fechaLimite);
        return days >= 0 && days <= 3;
    }
}
