package ru.lebedev.liga.validate;

public final class CheckCorrectCommand {
    private static final int COMMAND_DATE_LENGTH = 6;
    private static final int PERIOD_DATE_LENGTH = 8;
    private static final String DATE = "-date";

    private CheckCorrectCommand() {
    }

    public static boolean isValidCommand(String command) {
        String[] split = command.toLowerCase().split(" ");
        try {
            boolean rateCurrency = new RateCurrencyValidateImpl().checkCommand(split);
            boolean date_period = new DatePeriodValidateImpl().checkCommand(split);
            boolean algAlgorithm = new AlgAlgorithmValidateImpl().checkCommand(split);
            if (rateCurrency && date_period && algAlgorithm && split.length == COMMAND_DATE_LENGTH && command.contains(DATE)) {
                return true;
            }
            boolean outputList = new OutputListValidateImpl().checkCommand(split);
            if (rateCurrency && date_period && algAlgorithm && outputList && split.length == PERIOD_DATE_LENGTH) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}
