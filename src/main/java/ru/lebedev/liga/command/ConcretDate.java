package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.DataUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConcretDate extends AbstractCommand implements Command {
    private final static Pattern PATTERN = Pattern.compile("\\d\\d\\.\\d\\d\\.\\d{4}");

    public ConcretDate(CurrencyRepository repository, ForecastService forecastService, String command) {
        super(repository, forecastService, command);
    }

    @Override
    public String commandExecute() {
//        if (super.isCorrectCommand(super.getCommand())) {
        long between = 0;
        try {
            between = getBetweenNowAndPredictionDate();
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getParsedString(), e);

        }
        return getPredictionFromFuture(between);

//        return super.getCommand();
    }

    private String getPredictionFromFuture(long between) {

        ForecastService service = new ChooseNeedService(super.getCommand(), super.getRepository()).returnNeedService();
        Currency currency = super.getCurrency();

        CurrencyModel dayPrediction = null;
        for (int i = 0; i < between; i++) {
            dayPrediction = service.getDayPrediction(currency, i);
        }
        return correctOutput(dayPrediction);
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
