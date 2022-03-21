package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Actual extends AbstractAlgorithm {
    private final static int FIRST_ELEMENT = 0;
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;

    public Actual(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {

        List<CurrencyModel> allListRates = super.getRepository().getAllListRates(currency);

        LocalDate actualDateForPredictionAndFilter = TOMORROW.plusDays(index);

        List<CurrencyModel> twoYears = filterTwoYears(allListRates, actualDateForPredictionAndFilter);
        List<CurrencyModel> threeYears = filterThreeYears(allListRates, actualDateForPredictionAndFilter);


        BigDecimal twoYearsValue = twoYears.get(FIRST_ELEMENT).getValue();

        BigDecimal threeYearsValue = threeYears.get(FIRST_ELEMENT).getValue();

        BigDecimal nominal = allListRates.get(FIRST_ELEMENT).getNominal();

        return new CurrencyModel(nominal, actualDateForPredictionAndFilter, twoYearsValue.add(threeYearsValue), currency);
    }

    private List<CurrencyModel> filterThreeYears(List<CurrencyModel> allListRates, LocalDate actualDateForPredictionAndFilter) {
        return allListRates
                .stream()
                .filter(model -> model.getDate().isBefore(actualDateForPredictionAndFilter.plusDays(ONE).minusYears(THREE)))
                .collect(Collectors.toList());
    }

    private List<CurrencyModel> filterTwoYears(List<CurrencyModel> allListRates, LocalDate actualDateForPredictionAndFilter) {
        return allListRates
                .stream()
                .filter(model -> model.getDate().isBefore(actualDateForPredictionAndFilter.plusDays(ONE).minusYears(TWO)))
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
