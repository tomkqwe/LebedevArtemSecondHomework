package ru.lebedev.liga.repository;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;

import java.util.List;

public interface CurrencyRepository {
    List<CurrencyModel> getAllRates(Currency currency);
    List<CurrencyModel> getAllListRates(Currency currency);
    void addRate(CurrencyModel model,Currency currency);
}
