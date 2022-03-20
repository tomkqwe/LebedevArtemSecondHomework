package ru.lebedev.liga.validate;

import ru.lebedev.liga.model.Currency;

import java.util.Arrays;

public class RateCurrencyValidateImpl implements Validate {

    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

    @Override
    public boolean checkCommand(String[] command) {
        boolean rate = command[FIRST_ELEMENT].equals(RATE);
        boolean anyMatch = Arrays.stream(Currency.values())
                .map(String::valueOf)
                .anyMatch(s -> s.equals(command[SECOND_ELEMENT].toUpperCase()));

        return rate && anyMatch;
    }
}
