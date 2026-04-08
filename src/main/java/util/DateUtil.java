package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMAT     = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    // Private constructor — utility class, no instantiation
    private DateUtil() {}

    // Format LocalDate → String  e.g. 04/07/2026
    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(DATE_FORMAT);
    }

    // Format LocalDateTime → String  e.g. 04/07/2026 14:30
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATETIME_FORMAT);
    }

    // Parse String → LocalDate
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDate.parse(dateStr, DATE_FORMAT);
    }

    // Parse String → LocalDateTime
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) return null;
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMAT);
    }

    // Calculate days overdue (negative = not yet due)
    public static long daysOverdue(LocalDate dueDate) {
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    // Check if a date is already past
    public static boolean isOverdue(LocalDate dueDate) {
        return LocalDate.now().isAfter(dueDate);
    }

    // Calculate fine amount based on days overdue
    // e.g. 5.00 per day
    public static double calculateFine(LocalDate dueDate, double ratePerDay) {
        long days = daysOverdue(dueDate);
        if (days <= 0) return 0.00;
        return days * ratePerDay;
    }
}
