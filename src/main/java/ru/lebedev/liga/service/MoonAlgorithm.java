package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MoonAlgorithm extends AbstractAlgorithm {

    private final static int YEAR_NOW = 2022;
    private final static int ONE_YEAR_AGO = 2021;
    private final static List<LocalDate> FULL_MOON_2122 = Arrays.asList(LocalDate.of(YEAR_NOW, 1, 18)
            , LocalDate.of(YEAR_NOW, 2, 16), LocalDate.of(YEAR_NOW, 3, 18), LocalDate.of(YEAR_NOW, 4, 16),
            LocalDate.of(YEAR_NOW, 5, 16), LocalDate.of(YEAR_NOW, 6, 14), LocalDate.of(YEAR_NOW, 7, 13)
            , LocalDate.of(YEAR_NOW, 8, 12), LocalDate.of(YEAR_NOW, 9, 10), LocalDate.of(YEAR_NOW, 10, 9)
            , LocalDate.of(YEAR_NOW, 11, 8), LocalDate.of(YEAR_NOW, 12, 8), LocalDate.of(ONE_YEAR_AGO, 1, 28)
            , LocalDate.of(ONE_YEAR_AGO, 2, 27), LocalDate.of(ONE_YEAR_AGO, 3, 28), LocalDate.of(ONE_YEAR_AGO, 4, 27),
            LocalDate.of(ONE_YEAR_AGO, 5, 26), LocalDate.of(ONE_YEAR_AGO, 6, 24), LocalDate.of(ONE_YEAR_AGO, 7, 24)
            , LocalDate.of(ONE_YEAR_AGO, 8, 22), LocalDate.of(ONE_YEAR_AGO, 9, 21), LocalDate.of(ONE_YEAR_AGO, 10, 20)
            , LocalDate.of(ONE_YEAR_AGO, 11, 19), LocalDate.of(ONE_YEAR_AGO, 12, 19));

    public MoonAlgorithm(CurrencyRepository repository) {
        super(repository);
    }


    @Override
    public CurrencyModel getDayPrediction(Currency currency, int index) {
        List<LocalDate> fullMoonOrderByDesc = getOrderbydesc();

        List<CurrencyModel> allListRates = super.getRepository().getAllListRates(currency);

        LocalDate actualDateAndFilter = getActualDateForPredictionAndFilter(index);//

        List<CurrencyModel> threeFullMoonDates = collectThreeFullMoonDates(fullMoonOrderByDesc, allListRates);

        BigDecimal prediction = threeAverageMoonLightsValue(threeFullMoonDates);

        BigDecimal nominal = allListRates.get(allListRates.size() - 1).getNominal();

        return new CurrencyModel(nominal, actualDateAndFilter, prediction, currency);
    }

    private BigDecimal threeAverageMoonLightsValue(List<CurrencyModel> threeFullMoonDates) {

        BigDecimal sum = threeFullMoonDates
                .stream()
                .map(CurrencyModel::getValue)
                .reduce(BigDecimal::add)
                .get();//null check

        return sum.divide(new BigDecimal(threeFullMoonDates.size()), RoundingMode.FLOOR);

    }

    private List<CurrencyModel> collectThreeFullMoonDates(List<LocalDate> fullmoonOrderbydesc, List<CurrencyModel> allListRates) {
        return allListRates
                .stream()
                .filter(model -> fullmoonOrderbydesc.contains(model.getDate()))
                .sorted(Comparator.comparing(CurrencyModel::getDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private List<LocalDate> getOrderbydesc() {
        return FULL_MOON_2122
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private LocalDate getActualDateForPredictionAndFilter(int index) {
        return TOMORROW.plusDays(index);
    }

    private CurrencyModel getReccurentPrediction(Currency currency, ArrayList<CurrencyModel> list) {
        CurrencyModel recurrenctPrediction = list.get(list.size() - 1);
        BigDecimal reccurentValue = recurrenctPrediction.getValue();

        BigDecimal tenPercent = reccurentValue.multiply(new BigDecimal("0.1"));

        BigDecimal misunTenPercent = reccurentValue.subtract(tenPercent);
        BigDecimal addTenPercent = reccurentValue.add(tenPercent);

        BigDecimal resultValue = generateRandomNumbers(misunTenPercent, addTenPercent);
        resultValue = resultValue.setScale(2, RoundingMode.FLOOR);

        BigDecimal nominal = recurrenctPrediction.getNominal();
        LocalDate resultDate = recurrenctPrediction.getDate().plusDays(1);

        return new CurrencyModel(nominal, resultDate, resultValue, currency);
    }

    private BigDecimal generateRandomNumbers(BigDecimal misunTenPercent, BigDecimal addTenPercent) {
        double[] doubles = new Random()
                .doubles(1, misunTenPercent.doubleValue(), addTenPercent.doubleValue())
                .toArray();
        return BigDecimal.valueOf(doubles[0]);
    }


    public ArrayList<CurrencyModel> getWeekPrediction(Currency currency) {

        ArrayList<CurrencyModel> week = new ArrayList<>();
        for (int i = 0; i < WEEK; i++) {
            if (i == 0) {
                week.add(getDayPrediction(currency, i));
                continue;
            }
            CurrencyModel resultCurrencyModel = getReccurentPrediction(currency, week);

            week.add(resultCurrencyModel);

        }
        return week;
    }

    @Override
    public List<CurrencyModel> getMonthPrediction(Currency currency) {

        ArrayList<CurrencyModel> month = new ArrayList<>();
        for (int i = 0; i < MONTH; i++) {
            if (i == 0) {
                month.add(getDayPrediction(currency, i));
                continue;
            }
            CurrencyModel resultCurrencyModel = getReccurentPrediction(currency, month);

            month.add(resultCurrencyModel);

        }
        return month;

    }

}
