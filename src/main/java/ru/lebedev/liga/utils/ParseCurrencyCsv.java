package ru.lebedev.liga.utils;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Парсим и маппим данные из excel таблиц в модель.
 */
public class ParseCurrencyCsv {
    /**
     *
     * @param filepath путь до файла
     * @return list Model, в котором лежат все строки из таблицы
     * @throws IOException в случаи неверного имени файла будет выброшено исключение
     */
    public static List<CurrencyModel> parse(String filepath) throws IOException {
        List<String> fileLines;
        List<CurrencyModel> currencyModels = new ArrayList<>();
        try(InputStream in = ParseCurrencyCsv.class.getResourceAsStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            fileLines = reader.lines().toList();
        }
        for (int i = 1; i < fileLines.size(); i++) {
            String fileline = fileLines.get(i);
            final String[] split = fileline.split(";");
            final var columnList = new ArrayList<String>();
            for (String s : split) {
                if (isColumnPart(s)){
                    String lastText = columnList.get(columnList.size()-1);
                    columnList.set(columnList.size() - 1, lastText+","+s);
                }else {
                    columnList.add(s);
                }
            }
            CurrencyModel currencyModel = new CurrencyModel();
            currencyModel.setNominal(BigDecimal.valueOf(Double.parseDouble(columnList.get(0))));
            currencyModel.setDate(LocalDate.parse(columnList.get(1),DataUtil.PARSE_FORMATTER));
            currencyModel.setValue(BigDecimal.valueOf(Double.parseDouble(deleteQuotes(columnList.get(2)).replace(",","."))));
            currencyModel.setCurrency(getCurrency(columnList.get(3)));
            currencyModels.add(currencyModel);
        }
        return currencyModels;
    }

    public static Currency getCurrency(String s) {
        Currency currency;
        switch (s){
            case "Доллар США" -> currency = Currency.USD;
            case "Турецкая лира" -> currency = Currency.TRY;
            case "Евро" -> currency = Currency.EUR;
            case "Болгарский лев" -> currency = Currency.BGN;
            case "Армянский драм" -> currency = Currency.AMD;
            default -> currency = null;
        }
        return currency;
    }

    public static boolean isColumnPart(String s) {
        String textTrim = s.trim();
        return textTrim.indexOf("\"") == textTrim.lastIndexOf("\"") && textTrim.endsWith("\"");
    }
    public static String deleteQuotes(String s){
        return s.replaceAll("\"","");
    }
}
