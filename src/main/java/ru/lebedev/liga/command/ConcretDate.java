package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.DataUtil;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConcretDate extends AbstractCommand implements Command {
    private final static Pattern PATTERN = Pattern.compile("\\d\\d\\.\\d\\d\\.\\d{4}");

    public ConcretDate(CurrencyRepository repository, ForecastService forecastService, String command) {
        super(repository, forecastService, command);
    }

    @Override
    public String commandExecute() {
        if (CheckCorrectCommand.isValidCommand(super.getCommand())) {
            long between = getBetweenNowAndPredictionDate();
            return getPredictionFromFuture(between);
        }

        return getErrorCommand();
    }

    private String getPredictionFromFuture(long between) {

        ForecastService service = super.getService();
        Currency currency = super.getCurrency();

        CurrencyModel dayPrediction = null;
        for (int i = 0; i < between; i++) {
            dayPrediction = service.getDayPrediction(currency, i);
        }
        return correctOutput(Objects.requireNonNull(dayPrediction));
    }

    private long getBetweenNowAndPredictionDate() {
        LocalDate today = LocalDate.now();
        Matcher matcher = PATTERN.matcher(super.getCommand());
        String result = "";
        if (matcher.find()) {
            result = matcher.group();
        }
        LocalDate destiny = LocalDate.parse(result, DataUtil.PARSE_FORMATTER);
        return ChronoUnit.DAYS.between(today, destiny);

    }

}
