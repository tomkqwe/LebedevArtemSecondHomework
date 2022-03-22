package ru.lebedev.liga.utils;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Парсим и маппим данные из excel таблиц в модель.
 */
public class ParseCurrencyCsv {
    private static final String DELIMITER = ";";
    private static final String NOMINAL = "nominal";
    private static final String DATA = "data";
    private static final String CURS = "curs";
    private static final String CDX = "cdx";

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
        int nominal = 0;
        int data = 0;
        int curs = 0;
        int cdx = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            if (i == 0){
                String headline = fileLines.get(i);
                List<String> headlines = Arrays.asList(headline.split(DELIMITER));
                nominal = headlines.indexOf(NOMINAL);
                 data = headlines.indexOf(DATA);
                 curs = headlines.indexOf(CURS);
                 cdx = headlines.indexOf(CDX);
                 continue;
            }
            String fileline = fileLines.get(i);
            final String[] split = fileline.split(DELIMITER);
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
            currencyModel.setNominal(BigDecimal.valueOf(Double.parseDouble(columnList.get(nominal))));
            currencyModel.setDate(LocalDate.parse(columnList.get(data),DataUtil.PARSE_FORMATTER));
            currencyModel.setValue(BigDecimal.valueOf(Double.parseDouble(deleteQuotes(columnList.get(curs)).replace(",","."))));
            currencyModel.setCurrency(getCurrency(columnList.get(cdx)));
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
