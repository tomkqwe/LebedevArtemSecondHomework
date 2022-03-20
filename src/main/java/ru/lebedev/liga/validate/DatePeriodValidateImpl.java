package ru.lebedev.liga.validate;

import ru.lebedev.liga.utils.DataUtil;

import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePeriodValidateImpl implements Validate {
    private static final int THIRD_ELEMENT = 2;
    private static final int FOURTH_ELEMENT = 3;
    private final static String TOMORROW = "tomorrow";
    private final static String WEEK = "week";
    private final static String MONTH = "month";
    private final static Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");

    @Override
    public boolean checkCommand(String[] command) {
        boolean _date = command[THIRD_ELEMENT].equals(_DATE);
        boolean _period = command[THIRD_ELEMENT].equals(_PERIOD);

        boolean resultDateOrPeriod = checkDates(command);


        return (_date || _period) && resultDateOrPeriod;
    }
    private static boolean checkDates(String[] wordsInCommand) {
        String date_or_period = wordsInCommand[FOURTH_ELEMENT].toLowerCase();
        switch (date_or_period) {
            case TOMORROW:
            case WEEK:
            case MONTH:
                return true;
            default:
                Matcher matcher = DATE_PATTERN.matcher(date_or_period);
                if (matcher.find()) {
                    try {
                        LocalDate.parse(date_or_period, DataUtil.PARSE_FORMATTER);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }
        }
        return false;
    }
}
