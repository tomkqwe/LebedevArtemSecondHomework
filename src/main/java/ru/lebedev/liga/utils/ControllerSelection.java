package ru.lebedev.liga.utils;

import ru.lebedev.liga.controller.Controller;
import ru.lebedev.liga.controller.CurrencyController;
import ru.lebedev.liga.controller.DefaultController;
import ru.lebedev.liga.controller.SystemController;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.view.Console;

import java.util.Locale;

public class ControllerSelection {
    public static Controller getController(String command, ForecastService service, Console console) {
        String[] s = command.split(" ");
        return switch (s[0].toLowerCase()) {
            case "help", "contacts", "exit" -> new SystemController(console, s[0]);
            case "rate" -> new CurrencyController(s, console, service);
            default -> new DefaultController(console, command);
        };
    }
}
