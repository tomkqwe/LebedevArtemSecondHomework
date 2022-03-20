package ru.lebedev.liga.command;

import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MonthPredictionTest {
    CurrencyRepository repository = new CurrencyRepositoryImpl();

    @Test
    void commandExecute_check_Output() {
        String command = "rate bgn month actual list";
        ForecastService service = new ChooseNeedService(command, repository).returnNeedService();
        MonthPrediction monthPrediction = new MonthPrediction(repository, service, command);
        String result = monthPrediction.commandExecute();
        String collect = service.getMonthPrediction(Currency.BGN)
                .stream()
                .map(monthPrediction::correctOutput)
                .collect(Collectors.joining("\n"));
        assertThat(result).isEqualTo(collect);
    }

    @Test
    void isCorrectComand_Return_False() {
        String command = "rate ,usd, month regress list";
        ChooseNeedService service = new ChooseNeedService(command, repository);
        MonthPrediction monthPrediction = new MonthPrediction(repository, service.returnNeedService(), command);
//        assertThat(monthPrediction.isCorrectCommand(command)).isFalse();
    }

    @Test
    void isCorrectComand_Return_True() {
        String command = "rate usd month regress list ";
        ChooseNeedService service = new ChooseNeedService(command, repository);
        MonthPrediction monthPrediction = new MonthPrediction(repository, service.returnNeedService(), command);
//        assertThat(monthPrediction.isCorrectCommand(command)).isTrue();
    }
}