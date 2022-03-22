package ru.lebedev.liga.command;

import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseAlgorithm;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TomorrowPredictionTest {
    CurrencyRepositoryImpl repository = new CurrencyRepositoryImpl();


    @Test
    void check_Tomorrow_Prediction_Actual_USD() {
        TomorrowPrediction tomorrowPrediction = new TomorrowPrediction(repository,
                new ChooseAlgorithm("rate USD -date tomorrow -alg actual", repository).returnNeedService(),
                "rate USD -date tomorrow -alg actual");
        String correctOutput = tomorrowPrediction.correctOutput
                (new CurrencyModel(new BigDecimal("1"), LocalDate.now().plusDays(1), new BigDecimal("141.88"), Currency.USD));
        assertThat(correctOutput).isEqualTo(tomorrowPrediction.commandExecute());
    }
    @Test
    void check_Tomorrow_Prediction_Moon_EUR() {
        TomorrowPrediction tomorrowPrediction = new TomorrowPrediction(repository,
                new ChooseAlgorithm("rate EUR -date tomorrow -alg moon", repository).returnNeedService(),
                "rate EUR -date tomorrow -alg moon");
        String correctOutput = tomorrowPrediction.correctOutput
                (new CurrencyModel(new BigDecimal("1"), LocalDate.now().plusDays(1), new BigDecimal("85.12"), Currency.USD));
        assertThat(correctOutput).isEqualTo(tomorrowPrediction.commandExecute());
    }  @Test
    void check_Tomorrow_Prediction_Regress_BGN() {
        TomorrowPrediction tomorrowPrediction = new TomorrowPrediction(repository,
                new ChooseAlgorithm("rate BGN tomorrow regress", repository).returnNeedService(),
                "rate BGN tomorrow regress");
        String correctOutput = tomorrowPrediction.correctOutput
                (new CurrencyModel(new BigDecimal("1"), LocalDate.now().plusDays(1), new BigDecimal("59.58"), Currency.USD));
        assertThat(correctOutput).isEqualTo(tomorrowPrediction.commandExecute());
    }
}