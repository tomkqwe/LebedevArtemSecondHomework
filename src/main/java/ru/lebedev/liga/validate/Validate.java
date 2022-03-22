package ru.lebedev.liga.validate;

public interface Validate {
    String RATE = "rate";
    String _DATE = "-date";
    String _PERIOD = "-period";
    String _ALG = "-alg";
    String _OUTPUT = "-output";

    boolean checkCommand(String[] command);
}
