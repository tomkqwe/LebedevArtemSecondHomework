package ru.lebedev.liga.validate;

import ru.lebedev.liga.model.Currency;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OutputGraphValidateImpl implements Validate {
    private static final String GRAPH = "graph";
    private final static int SEVENTH_ELEMENT = 6;
    private final static int EIGHTH_ELEMENT = 7;
    private static final int SECOND_ELEMENT = 1;
    private static final int FIRST_ELEMENT = 0;
    private static final int LENGTH_OF_ONE_CURRENCY = 3;
    private static final String DELIMITER = ",";

    @Override
    public boolean checkCommand(String[] command) {
        boolean output = command[SEVENTH_ELEMENT].equals(_OUTPUT);
        boolean _graph = command[EIGHTH_ELEMENT].equals(GRAPH);
        boolean rate = command[FIRST_ELEMENT].equals(RATE);

        boolean currencies = checkCurrencyOrCurrencies(command);
        return output && _graph && currencies && rate;
    }

    private boolean checkCurrencyOrCurrencies(String[] command) {
        String currencies = command[SECOND_ELEMENT].toUpperCase();
        if (currencies.length() == LENGTH_OF_ONE_CURRENCY){
            try{
                Currency.valueOf(currencies);
            }catch (Exception e){
                return false;
            }
            return true;
        }else{
            try {
                List<Currency> collect = Arrays.stream(currencies.split(DELIMITER))
                        .map(Currency::valueOf)
                        .collect(Collectors.toList());
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
