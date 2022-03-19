package ru.lebedev.liga.service;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;

import java.util.List;

public interface ForecastService {
    CurrencyModel getDayPrediction(Currency currency,int index);
    List<CurrencyModel> getWeekPrediction(Currency currency);
    List<CurrencyModel> getMonthPrediction(Currency currency);
}
