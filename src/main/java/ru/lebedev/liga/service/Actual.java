package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Actual extends AbstractAlgorithm    {
    public Actual(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {
        LOGGER.debug("Алгоритм актуальный метод getDayPrediction");
        List<CurrencyModel> allListRates = super.getRepository().getAllListRates(currency);

        LocalDate actualDateForPredictionAndFilter = getActualDateForPredictionAndFilter(index);

        List<CurrencyModel> twoYears = filterTwoYears(allListRates, actualDateForPredictionAndFilter);
        List<CurrencyModel> threeYears = filterThreeYears(allListRates, actualDateForPredictionAndFilter);

        BigDecimal twoYearsValue = twoYears.get(twoYears.size() - 1).getValue();
        BigDecimal threeYearsValue = threeYears.get(threeYears.size() - 1).getValue();

        BigDecimal nominal = allListRates.get(allListRates.size() - 1).getNominal();

        return new CurrencyModel(nominal, actualDateForPredictionAndFilter, twoYearsValue.add(threeYearsValue), currency);
    }

    private List<CurrencyModel> filterThreeYears(List<CurrencyModel> allListRates, LocalDate actualDateForPredictionAndFilter) {
        return allListRates
                .stream()
                .filter(model -> model.getDate().isBefore(actualDateForPredictionAndFilter.plusDays(1).minusYears(3)))
                .collect(Collectors.toList());
    }

    private List<CurrencyModel> filterTwoYears(List<CurrencyModel> allListRates, LocalDate actualDateForPredictionAndFilter) {
        return allListRates
                .stream()
                .filter(model -> model.getDate().isBefore(actualDateForPredictionAndFilter.plusDays(1).minusYears(2)))
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
