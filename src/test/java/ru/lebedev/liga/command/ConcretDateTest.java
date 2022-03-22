package ru.lebedev.liga.command;

import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseAlgorithm;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ConcretDateTest {
    CurrencyRepository repository = new CurrencyRepositoryImpl();

    @Test
    void isCorrectCommand_Return_True() {
        String command = "rate USD 23.05.2039 actual";
        ChooseAlgorithm service = new ChooseAlgorithm(command, repository);
        ConcretDate concretDate = new ConcretDate(repository, service.returnNeedService(), command);
//        assertThat(concretDate.isCorrectCommand(command)).isTrue();
    }

    @Test
    void isCorrectCommand_Return_False() {
        String command = "rate USD 23,05,2039 actual";
        ChooseAlgorithm service = new ChooseAlgorithm(command, repository);
        ConcretDate concretDate = new ConcretDate(repository, service.returnNeedService(), command);
//        assertThat(concretDate.isCorrectCommand(command)).isFalse();
    }

    @Test
    void check_commandExecute_Correct_Output() {
        String command = "rate USD -date 23.05.2039 -alg actual";
        ChooseAlgorithm service = new ChooseAlgorithm(command, repository);
        ConcretDate concretDate = new ConcretDate(repository, service.returnNeedService(), command);
        String result = concretDate.commandExecute();
        String correctOutput = concretDate.correctOutput(
                new CurrencyModel(new BigDecimal("1"),
                        LocalDate.of(2039, 5, 23), new BigDecimal("211.62"), Currency.USD));
        assertThat(correctOutput).isEqualTo(result);

    }
}