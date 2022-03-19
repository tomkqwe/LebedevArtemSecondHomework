package ru.lebedev.liga.command;

import ru.lebedev.liga.command.dateCommand.DataOption;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.DataUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class ConcretDate extends AbstractCommand implements Command {

    public ConcretDate(CurrencyRepository repository, ForecastService forecastService, String command) {
        super(repository, forecastService, command);
    }

    @Override
    public String commandExecute() {
        LOGGER.debug("Обрабатываем команду на конкретную дату");
        if (super.isCorrectCommand(super.getCommand())) {
            long between = 0;
            try {
                between = getBetweenNowAndPredictionDate();
            } catch (DateTimeParseException e) {
                LOGGER.error(e.getParsedString(), e);
                return "После команды введите дату из будущего в формате день.месяц.год (дд.мм.гггг)";
            }
            return getPredictionFromFuture(between);
        }
        return super.getCommand();
    }

    private String getPredictionFromFuture(long between) {

        ForecastService service = new ChooseNeedService(super.getCommand(), super.getRepository()).returnNeedService();
        Currency currency = super.getCurrency();

        CurrencyModel dayPrediction = null;
        for (int i = 0; i < between; i++) {
            dayPrediction = service.getDayPrediction(currency, i);
        }
        String correctOutput = correctOutput(dayPrediction);
        LOGGER.debug("ConcretDate getPredictionFromFuture находим нужное значение из будущего: {}", correctOutput);
        return correctOutput;
    }

    private long getBetweenNowAndPredictionDate() {
        LocalDate today = LocalDate.now();
        String[] commandAndDate = super.getCommand().split(" ");
        LocalDate destiny = LocalDate.parse(commandAndDate[2], DataUtil.PARSE_FORMATTER);
        long between = ChronoUnit.DAYS.between(today, destiny);
        LOGGER.debug("ConcretDate getBetweenNowAndPredictionDate находим разницу в днях между сегодня и введенной датой: {}", between);
        return between;
    }

}
