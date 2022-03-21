package ru.lebedev.liga.command;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ScaleBuilder;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphPrediction {
    private final CurrencyRepository repository;
    private final ForecastService service;
    private final String command;
    private final Map<Currency, List<BigDecimal>> currencyListMap = new HashMap<>();

    public GraphPrediction(CurrencyRepository repository, ForecastService service, String command) {
        this.repository = repository;
        this.service = service;
        this.command = command;
    }

    public Map<Currency, List<BigDecimal>> getCurrencyListMap() {
        return currencyListMap;
    }

    public void commandExecute() throws PythonExecutionException, IOException {
       //peredelat
        String[] splitBySpace = command.split(" ");
        String currencies = splitBySpace[1];
        String weekOrMonth = splitBySpace[2];
        if (weekOrMonth.equals("week")) {
            initMapByWeek(currencies);
        } else if (weekOrMonth.equals("month")) {
            initMapByMonth(currencies);
        }

        Plot plt = Plot.create();
        addPlotBuilder(plt);
        plt.xscale(ScaleBuilder.Scale.linear);
        plt.yscale(ScaleBuilder.Scale.linear);
        plt.xlabel("value");
        plt.ylabel("value");
        plt.text(1, 1, "Days");
        plt.title("Graph of my currency");
        getGraphName(plt);
// Don't miss this line to output the file!
        plt.executeSilently();


    }

    private void getGraphName(Plot plt) {
        String fname = "graphics.png";

        plt.savefig(fname).dpi(200);
    }

    private void addPlotBuilder(Plot plt) {
        for (Currency currency1 : currencyListMap.keySet()) {
            plt.plot()
                    .add(currencyListMap.get(currency1))
                    .label("label")
                    .linestyle("-")
                    .linewidth(3.0);
        }

    }

    private void initMapByWeek(String s) {
        String[] splitCurrencys = s.toUpperCase(Locale.ROOT).split(",");
        for (String currency : splitCurrencys) {
            Currency key = Currency.valueOf(currency);
            currencyListMap.put(key, new WeekPrediction(repository, service, command).getWeekPrediction(key));
        }
    }

    private void initMapByMonth(String s) {
        String[] splitCurrencys = s.toUpperCase(Locale.ROOT).split(",");
        for (String currency : splitCurrencys) {
            Currency key = Currency.valueOf(currency);
            currencyListMap.put(key, new MonthPrediction(repository, service, command).getMonthPrediction(key));
        }
    }

    public boolean isCorrectCommand(String command) {
        String[] split = command.split(" ");
        String[] currencies = split[1].toUpperCase(Locale.ROOT).split(",");
        for (String currency : currencies) {
            try {
                Currency correctCurrency = Currency.valueOf(currency);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }
}
