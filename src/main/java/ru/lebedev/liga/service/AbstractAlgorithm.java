package ru.lebedev.liga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAlgorithm implements ForecastService {
    public final static int DAY = 1;
    public final static int WEEK = 7;
    public final static int MONTH = 30;
    public final static LocalDate TOMORROW = LocalDate.now().plusDays(DAY);
    private final  CurrencyRepository repository;

    public final static Logger LOGGER = LoggerFactory.getLogger(AbstractAlgorithm.class);

    public CurrencyRepository getRepository() {
        return repository;
    }

    public AbstractAlgorithm(CurrencyRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<CurrencyModel> getWeekPrediction(Currency currency) {

        List<CurrencyModel> list = new ArrayList<>();
        for (int i = 0; i < WEEK; i++) {
            list.add(getDayPrediction(currency, i));
        }
        return list;
    }

    @Override
    public List<CurrencyModel> getMonthPrediction(Currency currency) {

        List<CurrencyModel> list = new ArrayList<>();
        for (int i = 0; i < MONTH; i++) {
            list.add(getDayPrediction(currency, i));
        }
        return list;
    }


}
