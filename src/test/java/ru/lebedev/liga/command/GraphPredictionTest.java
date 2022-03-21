package ru.lebedev.liga.command;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseAlgorithm;
import ru.lebedev.liga.service.ForecastService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GraphPredictionTest {
    CurrencyRepository repository = new CurrencyRepositoryImpl();

    @Test
    void commandExecute_Check_Maps_To_Equals() throws PythonExecutionException, IOException {
        String command = "rate USD,EUR,BGN week actual graph";
        ForecastService service = new ChooseAlgorithm(command, repository).returnNeedService();
        GraphPrediction graphPrediction = new GraphPrediction(service, command);
        graphPrediction.commandExecute();
        Map<Currency, List<BigDecimal>> currencyListMap = graphPrediction.getCurrencyListMap();
        Map<Currency, List<BigDecimal>> currencyTestMap = new HashMap<>();
        String[] split = command.split(" ");
        String[] currencies = split[1].split(",");
        for (String currency : currencies) {
            Currency key = Currency.valueOf(currency);
            currencyTestMap.put(key, new WeekPrediction(repository, service, command).getWeekPrediction(key));
        }
        assertThat(currencyListMap.get(Currency.USD)).isEqualTo(currencyTestMap.get(Currency.USD));
        assertThat(currencyListMap.get(Currency.EUR)).isEqualTo(currencyTestMap.get(Currency.EUR));
        assertThat(currencyListMap.get(Currency.BGN)).isEqualTo(currencyTestMap.get(Currency.BGN));
    }
}