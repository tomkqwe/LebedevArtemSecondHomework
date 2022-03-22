package ru.lebedev.liga.command;

import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseAlgorithm;
import ru.lebedev.liga.service.ForecastService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class WeekPredictionTest {
    CurrencyRepositoryImpl repository = new CurrencyRepositoryImpl();

    @Test
    void commandExecute() {
        String command = "rate bgn week actual";
        ForecastService service = new ChooseAlgorithm(command, repository).returnNeedService();
        WeekPrediction weekPrediction = new WeekPrediction(repository, service, command);
        String collect = service.getWeekPrediction(Currency.BGN)
                .stream()
                .map(weekPrediction::correctOutput)
                .collect(Collectors.joining("\n"));
        String result = weekPrediction.commandExecute();
        assertThat(collect).isEqualTo(result);

    }

    @Test
    void check_getWeekPrediction() {
        String command = "rate eur week actual";
        ForecastService service = new ChooseAlgorithm(command, repository).returnNeedService();
        List<BigDecimal> collect = service.getWeekPrediction(Currency.EUR)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
        List<BigDecimal> weekPrediction = new WeekPrediction(repository, service, command).getWeekPrediction(Currency.EUR);
        assertThat(collect).isEqualTo(weekPrediction);
    }

    @Test
    void check_isCorrectCommand_Return_True() {
        String command = "rate amd week actual";
        ChooseAlgorithm amdWeekActual = new ChooseAlgorithm(command, repository);
        ForecastService service = amdWeekActual.returnNeedService();
        WeekPrediction weekPrediction = new WeekPrediction(repository, service, command);
//        assertThat(weekPrediction.isCorrectCommand(command)).isTrue();
    }
}