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
    private final static int DAYS_IN_MONTH = 30;
    private final static int FIRST_ELEMENT = 0;


    public LineurRegressionImpl(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {

        List<CurrencyModel> list = super.getRepository().getAllListRates(currency);

        LocalDate dateForPrediction = TOMORROW.plusDays(index);

        List<CurrencyModel> modelsForOneMonth = getModelsForOneMonth(list);

        double[] valuesX = getValuesX(modelsForOneMonth);

        BigDecimal nominal = list.get(FIRST_ELEMENT).getNominal();


        LinearRegression linearRegression = new LinearRegression(valuesX, valuesX);

        double valueX = getValue(index, valuesX);
        BigDecimal newPrediction = new BigDecimal(String.valueOf(linearRegression.predict(valueX)));

        return new CurrencyModel(nominal, dateForPrediction, newPrediction, currency);
    }

    private double getValue(int index, double[] valuesX) {
        index %= DAYS_IN_MONTH;
        return valuesX[index];
    }


    private double[] getValuesX(List<CurrencyModel> modelsForOneMonth) {

        return modelsForOneMonth
                .stream()
                .map(model -> String.valueOf(model.getValue()))
                .map(Double::parseDouble)
                .mapToDouble(value -> value)
                .toArray();
    }


    private List<CurrencyModel> getModelsForOneMonth(List<CurrencyModel> list) {

        return list
                .stream()
                .sorted(Comparator.comparing(CurrencyModel::getDate).reversed())
                .limit(DAYS_IN_MONTH)
                .collect(Collectors.toList());
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
