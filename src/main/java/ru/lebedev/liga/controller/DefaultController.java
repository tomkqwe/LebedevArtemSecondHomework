package ru.lebedev.liga.controller;

import ru.lebedev.liga.view.Console;

/**
 * Обрабатываем некорректные команды
 */
public class DefaultController implements Controller {
    private final Console console;
    private final String command;

    public DefaultController(Console console, String command) {
        this.console = console;
        this.command = command;
    }

    @Override
    public void operate() {
        console.printMessage("Введена неверная команда: " + "\"" + command + "\"");
        console.printMessage("Для просмотра списка доступных команд введите \"help\"");
    }
}
