package ru.lebedev.liga.validate;

import java.util.List;

public class OutputListValidateImpl implements Validate {
    private final static int SEVENTH_ELEMENT = 6;
    private final static int EIGHTH_ELEMENT = 7;
    private final static String LIST = "list";

    @Override
    public boolean checkCommand(String[] command) {
        boolean _output = command[SEVENTH_ELEMENT].equals(_OUTPUT);
        boolean list = command[EIGHTH_ELEMENT].equals(LIST);
        return _output && list;
    }
}
