package ru.lebedev.liga.view;

import ru.lebedev.liga.Main;
import ru.lebedev.liga.model.CurrencyModel;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.println("¬ведите комманду: ");
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printDayPrediction(CurrencyModel model) {
        printMessage(String.format("%s - %s - %s",model.getNominal(), model.getDate().format(DateTimeFormatter.ISO_DATE), String.format("%.2f", model.getValue().multiply(model.getNominal()))));
    }
    public void printWeekPrediction(List<CurrencyModel> list){
        list.forEach(this::printDayPrediction);
    }
}