package ru.lebedev.liga.controller;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.view.Console;

import java.util.Locale;

public class CurrencyController implements Controller {
    private final String[] commands;
    private final Console console;
    private final ForecastService service;

    public CurrencyController(String[] commands, Console console, ForecastService service) {
        this.commands = commands;
        this.console = console;
        this.service = service;
    }

    @Override
    public void operate() {
        Currency currency;
        if (commands.length < 3) {
            console.printMessage("����� ������� \"rate\" ���������� ������� ������������ ������ � ������ ��������.\n" +
                    "��� ��������� ������ ��������� ������ ������� \"help\"");
            return;
        }
        try {
            currency = Currency.valueOf(commands[1].toUpperCase(Locale.ROOT));
        }catch (IllegalArgumentException e){
            console.printMessage("����� ������� \"rate\" ���������� ������� ����������� ������������ ������ (USD, EUR, TRY)");
            return;
        }
        switch (commands[2].toLowerCase(Locale.ROOT)){
            case "tomorrow" -> console.printDayPrediction(service.getDayPrediction(currency, 0));
            case "week" -> console.printWeekPrediction(service.getWeekPrediction(currency));
            default -> console.printMessage("����� ������� \"rate\" � ������������ ������ ���������� ������� ������ �������� (tomorrow, week)");
        }
    }
}
