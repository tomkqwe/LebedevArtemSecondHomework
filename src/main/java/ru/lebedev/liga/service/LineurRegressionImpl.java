package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LineurRegressionImpl extends AbstractAlgorithm {

    public LineurRegressionImpl(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {
        LOGGER.debug("Алгоритм Линейной регрессии, метод getDayPrediction");
        List<CurrencyModel> list = super.getRepository().getAllListRates(currency);

        LocalDate dateForPrediction = getActualDateForPredictionAndFilter(index);

        List<CurrencyModel> modelsForOneMonth = getModelsForOneMonth(list);

        double[] valuesX = getValuesX(modelsForOneMonth);

        BigDecimal nominal = list.get(0).getNominal();

        final var linearRegression = new LinearRegression(valuesX, valuesX);

        double valueX = getValue(index, valuesX);
        BigDecimal newPrediction = new BigDecimal(String.valueOf(linearRegression.predict(valueX)));

        return new CurrencyModel(nominal, dateForPrediction, newPrediction, currency);
    }

    private double getValue(int index, double[] valuesX) {
        index %= 30;
        LOGGER.debug("LineurRegressionImpl метод getValue, получаем конкретное значение {}",valuesX[index]);
        return valuesX[index];
    }


    private double[] getValuesX(List<CurrencyModel> modelsForOneMonth) {
        LOGGER.debug("LineurRegressionImpl метод getValuesX, получаем значения валюты");
        return modelsForOneMonth
                .stream()
                .map(model -> String.valueOf(model.getValue()))
                .map(Double::parseDouble)
                .mapToDouble(value -> value)
                .toArray();
    }


    private List<CurrencyModel> getModelsForOneMonth(List<CurrencyModel> list) {
        LOGGER.debug("LineurRegressionImpl метод getModelsForOneMonth, экстраполируем по последнему месяцу");
        return list
                .stream()
                .sorted(Comparator.comparing(CurrencyModel::getDate).reversed())
                .limit(30)
                .collect(Collectors.toList());
    }

    private LocalDate getActualDateForPredictionAndFilter(int index) {
        return TOMORROW.plusDays(index);
    }

    @Override
    public List<CurrencyModel> getWeekPrediction(Currency currency) {
        return super.getWeekPrediction(currency);
    }

    @Override
    public List<CurrencyModel> getMonthPrediction(Currency currency) {
        return super.getMonthPrediction(currency);
    }
}
