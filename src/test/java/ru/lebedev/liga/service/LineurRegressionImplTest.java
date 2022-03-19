package ru.lebedev.liga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LineurRegressionImplTest {
    @Mock
    CurrencyRepository repository = new CurrencyRepositoryImpl();
    Currency aTry = Currency.TRY;

    @Test
    void check_Day_Prediction_AMD_TRY() {
        BigDecimal testNumberTry = new BigDecimal("74.4922");
        BigDecimal testNumberAmd = new BigDecimal("21.0112");
        BigDecimal valueTry = new LineurRegressionImpl(repository).getDayPrediction(aTry, 0).getValue();
        BigDecimal valueAmd = new LineurRegressionImpl(repository).getDayPrediction(Currency.AMD, 0).getValue();
        assertThat(valueTry).isEqualTo(testNumberTry);
        assertThat(valueAmd).isEqualTo(testNumberAmd);
    }

    @Test
    void check_Week_Prediction_And_Seven_Month_Prediction() {
        List<CurrencyModel> weekPrediction = new LineurRegressionImpl(repository).getWeekPrediction(aTry);
        List<CurrencyModel> sevenFirstFromMonth = new LineurRegressionImpl(repository).getMonthPrediction(aTry)
                .stream()
                .limit(7)
                .collect(Collectors.toList());
        assertThat(weekPrediction).isEqualTo(sevenFirstFromMonth);
    }

    @Test
    void check_Last_Date_From_Prediction_By_Month() {
        List<CurrencyModel> monthPrediction = new LineurRegressionImpl(repository).getMonthPrediction(aTry);
        LocalDate PlusMonth = LocalDate.now().plusDays(30);
        assertThat(monthPrediction.get(monthPrediction.size() - 1).getDate()).isEqualTo(PlusMonth);
    }
}