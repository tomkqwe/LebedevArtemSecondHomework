package ru.lebedev.liga.command.GraphsCommands;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.service.ForecastService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class GraphMonthOrWeekPrediction {
    private ForecastService service;

    public GraphMonthOrWeekPrediction(ForecastService service) {
        this.service = service;
    }

    public List<BigDecimal> getMonthPrediction(Currency currency) {
        return service
                .getMonthPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }
    public List<BigDecimal> getWeekPrediction(Currency currency) {
        return service
                .getWeekPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }
}
