package ru.lebedev.liga.utils;

import java.time.format.DateTimeFormatter;

public class DataUtil {
    public static final DateTimeFormatter PARSE_FORMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
}
