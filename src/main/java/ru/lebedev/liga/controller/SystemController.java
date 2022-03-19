package ru.lebedev.liga.controller;

import ru.lebedev.liga.view.Console;

import java.util.Locale;

/**
 * Обрабатываем служебные команды
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
                                        
                    Cписок доступных комманд:
                        help     - помощь
                        rate     - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EUR, TRY),
                                       а так же период прогноза, на 1 день или на неделю (tomorrow или week)
                                       параметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        exit     - завершение программы
                        contacts - обратная связь
                        """);
            case "contacts" -> console.printMessage("Спасибо @MarinoSpb, за предоставленное решение, ты крут!" +
                    " А меня зовут @tomkqwe");
            case "exit" ->{
                console.printMessage("Application stopped");
                System.exit(0);
            }
        }

    }
}
