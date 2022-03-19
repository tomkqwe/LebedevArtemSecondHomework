package ru.lebedev.liga.command.dateCommand;

import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.command.ConcretDate;
import ru.lebedev.liga.command.ErrorMessage;
import ru.lebedev.liga.command.TomorrowPrediction;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.DataUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDateDataOptionImpl implements DataOption {
    private static final String TOMORROW = "tomorrow";
    private static final Pattern DATE_FROM_FUTURE = Pattern.compile("[0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}");


    @Override
    public Command returnDatePeriodWhatYouWant(CurrencyRepository repository, ForecastService service, String command) {
        String[] wordsInCommand = command.toLowerCase().split(" ");
        if (Arrays.asList(wordsInCommand).contains(TOMORROW)) {
            return new TomorrowPrediction(repository, service, command);
        }
        Matcher matcher = DATE_FROM_FUTURE.matcher(command);
        ErrorMessage errorMessage = new ErrorMessage(command);
        if (matcher.find()) {
            String maybeDate = matcher.group();
            try {
                LocalDate.parse(maybeDate, DataUtil.PARSE_FORMATTER);
                return new ConcretDate(repository, service, command);
            } catch (DateTimeParseException e) {
                return errorMessage;
            }
        }
        return errorMessage;
    }

}
