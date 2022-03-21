package ru.lebedev.liga.command;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ScaleBuilder;
import ru.lebedev.liga.command.GraphsCommands.GraphMonthOrWeekPrediction;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.service.ForecastService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphPrediction implements Command {
    private final static String WEEK = "week";
    private final static String MONTH = "month";
    private final static int SECOND_ELEMENT = 1;
    private final static int FOURTH_ELEMENT = 3;
    private final ForecastService service;
    private final String command;
    private final Map<Currency, List<BigDecimal>> currencyListMap = new HashMap<>();

    public GraphPrediction(ForecastService service, String command) {
        this.service = service;
        this.command = command;
    }

    public Map<Currency, List<BigDecimal>> getCurrencyListMap() {
        return currencyListMap;
    }

    @Override
    public String commandExecute() {
        return returnGraphName(executeGraph());

    }

    public Plot executeGraph() {
        splitAndCheckCommand();
        Plot plt = Plot.create();
        addPlotBuilder(plt);
        plt.xscale(ScaleBuilder.Scale.linear);
        plt.yscale(ScaleBuilder.Scale.linear);
        plt.xlabel("Date");
        plt.ylabel("CurrencyValue");
        plt.text(1, 1, "Days");
        plt.title("Graph of my currency");
        returnGraphName(plt);
// Don't miss this line to output the file!
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
        return plt;
    }

    private void splitAndCheckCommand() {
        String[] splitBySpace = command.split(" ");
        String currencies = splitBySpace[SECOND_ELEMENT];
        String weekOrMonth = splitBySpace[FOURTH_ELEMENT];
        if (weekOrMonth.equals(WEEK)) {
            initMapByWeek(currencies);
        } else if (weekOrMonth.equals(MONTH)) {
            initMapByMonth(currencies);
        }
    }

    public String returnGraphName(Plot plt) {
        String fname = "graphics.png";
        plt.savefig(fname).dpi(200);
        return fname;

    }

    private void addPlotBuilder(Plot plt) {
        for (Currency currency1 : currencyListMap.keySet()) {
            plt.plot()
                    .add(currencyListMap.get(currency1))
                    .label(currency1.toString())
                    .linestyle("-")
                    .linewidth(3.0);
        }

    }

    private void initMapByWeek(String s) {
        String[] curriencies = s.toUpperCase().split(",");
        for (String currency : curriencies) {
            Currency key = Currency.valueOf(currency);
            currencyListMap.put(key, new GraphMonthOrWeekPrediction(service).getWeekPrediction(key));
        }
    }

    private void initMapByMonth(String s) {
        String[] curriencies = s.toUpperCase().split(",");
        for (String currency : curriencies) {
            Currency key = Currency.valueOf(currency);
            currencyListMap.put(key, new GraphMonthOrWeekPrediction(service).getMonthPrediction(key));
        }
    }


}
