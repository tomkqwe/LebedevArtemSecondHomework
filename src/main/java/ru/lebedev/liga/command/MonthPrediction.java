package ru.lebedev.liga.command;

import ru.lebedev.liga.command.dateCommand.DataOption;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MonthPrediction extends AbstractCommand implements Command {
    public MonthPrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);
    }

    @Override
    public String commandExecute() {
//        if (super.isCorrectCommand(super.getCommand())) {
            return getListWithMonthPredictions()
                    .stream()
                    .map(super::correctOutput)
                    .collect(Collectors.joining("\n"));

//        return super.writeMessage();
    }

    private List<CurrencyModel> getListWithMonthPredictions() {
        LOGGER.debug("MonthPrediction метод getListWithMonthPredictions получаем список с прогнозами");
        ForecastService service = new ChooseNeedService(super.getCommand(),super.getRepository()).returnNeedService();
        return service.getMonthPrediction(super.getCurrency());

    }


    public List<BigDecimal> getMonthPrediction(Currency currency) {
        LOGGER.debug("MonthPrediction метод getMonthPrediction получаем список со значениями валюты");

        return super.getService()
                .getMonthPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }

}
