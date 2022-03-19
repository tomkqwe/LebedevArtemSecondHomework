package ru.lebedev.liga.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.utils.ParseCurrencyCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyRepositoryImpl implements CurrencyRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(CurrencyRepositoryImpl.class);
    private static List<CurrencyModel> usd = new ArrayList<>();
    private static List<CurrencyModel> euro = new ArrayList<>();
    private static List<CurrencyModel> lira = new ArrayList<>();
    private static List<CurrencyModel> lev = new ArrayList<>();
    private static List<CurrencyModel> dram = new ArrayList<>();

    static {
        try {
            LOGGER.debug("Инициализируем пути до файлов с валютой");
            usd = ParseCurrencyCsv.parse("/USD_F01_02_2005_T05_03_2022.csv");
            euro = ParseCurrencyCsv.parse("/EUR_F01_02_2005_T05_03_2022.csv");
            lira = ParseCurrencyCsv.parse("/TRY_F01_02_2005_T05_03_2022.csv");
            lev = ParseCurrencyCsv.parse(("/BGN_F01_02_2005_T05_03_2022.csv"));
            dram = ParseCurrencyCsv.parse("/AMD_F01_02_2005_T05_03_2022.csv");
        } catch (IOException e) {
          LOGGER.error("Ошибка загрузки данных",e.getCause());
        }
    }

    @Override
    public List<CurrencyModel> getAllRates(Currency currency) {
        LOGGER.debug("Каждой валюте назначаем свой список");
        return switch (currency){
            case USD -> usd;
            case EUR -> euro;
            case TRY -> lira;
            case BGN -> lev;
            case AMD -> dram;
        };
    }

    @Override
    public List<CurrencyModel> getAllListRates(Currency currency) {
        LOGGER.debug("Сортируем по дате каждый список");
        List<CurrencyModel> list = getAllRates(currency);
        list = list.stream()
                .sorted(Comparator.comparing(CurrencyModel::getDate))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public void addRate(CurrencyModel model, Currency currency) {
        LOGGER.debug("Добавляем в спискок модель");
        List<CurrencyModel> list = getAllRates(currency);
        list.add(model);
    }
}
