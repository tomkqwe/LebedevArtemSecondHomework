//package ru.lebedev.liga.utils;
//
//import ru.lebedev.liga.model.Currency;
//
//import java.time.LocalDate;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public final class CorrectCommandUtil {
//
//    private final static String RATE = "rate";
//    private final static String _DATE = "-date";
//    private final static String _PERIOD = "-period";
//    private final static String _ALG = "-alg";
//    private final static String _OUTPUT = "-output";
//    private final static String LIST = "list";
//    private final static String TOMORROW = "tomorrow";
//    private final static String WEEK = "week";
//    private final static String MONTH = "month";
//    private final static String MOON = "moon";
//    private final static String ACTUAL = "actual";
//    private final static String REGRESS = "regress";
//    private final static Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
//    private final static int FIRST_ELEMENT = 0;
//    private final static int SECOND_ELEMENT = 1;
//    private final static int THIRD_ELEMENT = 2;
//    private final static int FOURTH_ELEMENT = 3;
//    private final static int FIFTH_ELEMENT = 4;
//    private final static int SIXTH_ELEMENT = 5;
//    private final static int SEVENTH_ELEMENT = 6;
//    private final static int EIGHTH_ELEMENT = 7;
//
//    private CorrectCommandUtil() {
//    }
//
//    public static boolean isValidCommand(String command) {
//        String[] wordsInCommand = command.toLowerCase().split(" ");
//
//        boolean firstCheck = wordsInCommand[FIRST_ELEMENT].equals(RATE);
//        boolean secondCheck = checkCurriencies(wordsInCommand);
//
//        boolean thirdCheck_date = wordsInCommand[THIRD_ELEMENT].equals(_DATE);
//        boolean thirdCheck_period = wordsInCommand[THIRD_ELEMENT].equals(_PERIOD);
//
//        if (thirdCheck_period && wordsInCommand.length < 8) {
//            return false;
//        }
//
//        boolean fourCheck = checkDates(wordsInCommand);
//        boolean fiveCheck = wordsInCommand[FIFTH_ELEMENT].equals(_ALG);
//
//        boolean sixCheck = checkAlgo(wordsInCommand);
//
//        if (secondCheck && thirdCheck_date && fourCheck && fiveCheck && sixCheck) {
//            return true;
//        }
//        boolean sevenCheck = wordsInCommand[SEVENTH_ELEMENT].equals(_OUTPUT);
//
//        boolean eighthCheck = checkOutput(wordsInCommand);
//        return firstCheck && secondCheck && thirdCheck_period && fourCheck && fiveCheck && sixCheck && sevenCheck && eighthCheck;
//
//    }
//
//    private static boolean checkOutput(String[] wordsInCommand) {
//        String output = wordsInCommand[EIGHTH_ELEMENT].toLowerCase();
//        return output.equals(LIST);
//
//    }
//
//    private static boolean checkAlgo(String[] wordsInCommand) {
//        String algos = wordsInCommand[SIXTH_ELEMENT].toLowerCase();
//        if (algos.equals(MOON)) {
//            return true;
//        }
//        if (algos.equals(ACTUAL)) {
//            return true;
//        }
//        return algos.equals(REGRESS);
//    }
//
//    private static boolean checkDates(String[] wordsInCommand) {
//        String date_or_period = wordsInCommand[FOURTH_ELEMENT].toLowerCase();
//        switch (date_or_period) {
//            case TOMORROW:
//            case WEEK:
//            case MONTH:
//                return true;
//            default:
//                Matcher matcher = DATE_PATTERN.matcher(date_or_period);
//                if (matcher.find()) {
//                    try {
//                        LocalDate.parse(date_or_period, DataUtil.PARSE_FORMATTER);
//                    } catch (Exception e) {
//                        return false;
//                    }
//                    return true;
//                }
//        }
//        return false;
//    }
//
//    private static boolean checkCurriencies(String[] wordsInCommand) {
//        String input = wordsInCommand[SECOND_ELEMENT].toUpperCase();
//        try {
//            Currency.valueOf(input);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//
//    }
//}
