package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class WeekPrediction extends AbstractCommand implements Command {

    public WeekPrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);
    }

    @Override
    public String commandExecute() {
        LOGGER.debug("WeekPrediction commandExecute , если комманда соответсвует шаблону, " +
                "выводим ее иначе выводим сообщение о неверной команде");
        if (super.isCorrectCommand(super.getCommand())) {
            return getListWithWeekPredictions()
                    .stream()
                    .map(super::correctOutput)
                    .collect(Collectors.joining("\n"));

        }
        return super.writeMessage();
    }

    private List<CurrencyModel> getListWithWeekPredictions() {
        LOGGER.debug("WeekPrediction метод getListWithWeekPredictions получаем список с прогнозами");
        ForecastService service = new ChooseNeedService(super.getCommand(), super.getRepository()).returnNeedService();
        return service.getWeekPrediction(super.getCurrency());
    }

    public List<BigDecimal> getWeekPrediction(Currency currency) {
        LOGGER.debug("WeekPrediction метод getWeekPrediction получаем список со значениями валюты");
        return super.getService()
                .getWeekPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }


}
