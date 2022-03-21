package ru.lebedev.liga.command.dateCommand;

import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.command.ErrorMessage;
import ru.lebedev.liga.command.MonthPrediction;
import ru.lebedev.liga.command.WeekPrediction;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;

import java.util.Arrays;
import java.util.List;

public class CommandPeriodDataOptionImpl implements DataOption {
    private static final String WEEK = "week";
    private static final String MONTH = "month";

    @Override
    public Command returnDatePeriodWhatYouWant(CurrencyRepository repository, ForecastService service, String command) {
        List<String> wordsInCommand = Arrays.asList(command.toLowerCase().split(" "));
        if (wordsInCommand.contains(WEEK)) {
            return new WeekPrediction(repository, service, command);
        } else if (wordsInCommand.contains(MONTH)) {
            return new MonthPrediction(repository, service, command);
        }
        return new ErrorMessage(command);
    }
}
