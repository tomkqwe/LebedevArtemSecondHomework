package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AverageArithmeticService implements ForecastService{
    private final CurrencyRepository repository;
    private final static int DAY = 1;
    private final static int WEEK = 7;

    public AverageArithmeticService(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {
      List<CurrencyModel> list = repository.getAllListRates(currency);
      supplementCurrency(currency,list);
      return getDayPredictionFromList(currency,list);
    }

    @Override
    public List<CurrencyModel> getWeekPrediction(Currency currency) {
        List<CurrencyModel> list = repository.getAllListRates(currency);
        supplementCurrency(currency,list);
        List<CurrencyModel> forecastWeekRate = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CurrencyModel model = getDayPredictionFromList(currency,list);
            list.add(model);
            forecastWeekRate.add(model);
        }
        return forecastWeekRate;
    }

    @Override
    public List<CurrencyModel> getMonthPrediction(Currency currency) {
        return null;
    }

    private void supplementCurrency(Currency currency, List<CurrencyModel> list) {
        while (!Objects.equals(list.get(list.size()-1).getDate(), LocalDate.now())){
            list.add(getDayPredictionFromList(currency,list));
        }
    }

    private CurrencyModel getDayPredictionFromList(Currency currency, List<CurrencyModel> list) {
        List<BigDecimal> listPredictionFromSevenDays = list
                .stream()
                .sorted(Comparator.comparing(CurrencyModel::getDate)
                        .reversed())
                .limit(7)
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());

            return new CurrencyModel(list.get(list.size()-1).getNominal(),list.get(list.size()-1).getDate().plusDays(1)
                    ,average(listPredictionFromSevenDays, RoundingMode.FLOOR)
            ,currency);
    }

    private BigDecimal average(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return sum.divide(new BigDecimal(bigDecimals.size()),roundingMode);
    }
}
