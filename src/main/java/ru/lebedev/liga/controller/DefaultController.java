package ru.lebedev.liga.controller;

import ru.lebedev.liga.view.Console;

/**
 * ������������ ������������ �������
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
        console.printMessage("������� �������� �������: " + "\"" + command + "\"");
        console.printMessage("��� ��������� ������ ��������� ������ ������� \"help\"");
    }
}
