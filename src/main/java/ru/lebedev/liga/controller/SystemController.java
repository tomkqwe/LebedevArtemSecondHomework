package ru.lebedev.liga.controller;

import ru.lebedev.liga.view.Console;

import java.util.Locale;

/**
 * ������������ ��������� �������
 */
public class SystemController implements Controller{
   private final Console console;
   private final String command;

    public SystemController(Console console, String command) {
        this.console = console;
        this.command = command;
    }

    @Override
    public void operate() {
        switch (command.toLowerCase(Locale.ROOT)){
            case "help" -> console.printMessage("""
                                        
                    C����� ��������� �������:
                        help     - ������
                        rate     - ������� ������ �����, � ���� �������� ���������� ��������� ������ (USD, EUR, TRY),
                                       � ��� �� ������ ��������, �� 1 ���� ��� �� ������ (tomorrow ��� week)
                                       ��������� ������� ���������� ��������� ����� ������ (������: rate TRY tomorrow)
                        exit     - ���������� ���������
                        contacts - �������� �����
                        """);
            case "contacts" -> console.printMessage("������� @MarinoSpb, �� ��������������� �������, �� ����!" +
                    " � ���� ����� @tomkqwe");
            case "exit" ->{
                console.printMessage("Application stopped");
                System.exit(0);
            }
        }

    }
}
