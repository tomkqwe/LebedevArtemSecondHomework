package ru.lebedev.liga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ActualTest {
    @Mock
    CurrencyRepository repository = new CurrencyRepositoryImpl();

    @Test
    void getDayPrediction_Check_length_and_check_first_element() {
        List<CurrencyModel> allListRates = repository.getAllListRates(Currency.USD);
        assertThat(allListRates.size()).isEqualTo(4233);
        assertThat(allListRates.get(0)).isEqualTo(new CurrencyModel(
                new BigDecimal("1.0"), LocalDate.of(2005, 2, 1), new BigDecimal("28.1136"), Currency.USD));
    }

    @Test
    void getWeekPrediction_Check_dates_At_week() {
        Currency usd = Currency.USD;
        LocalDate localDate = LocalDate.now().plusDays(1);
        ArrayList<LocalDate> localDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            localDates.add(localDate.plusDays(i));
        }
        assertThat(new Actual(repository).getWeekPrediction(usd)
                .stream()
                .map(CurrencyModel::getDate)
                .collect(Collectors.toList()))
                .isEqualTo(localDates);
    }

    @Test
    void check_getDayPrediction_and_getMonthPrediction_First_Element() {
        CurrencyModel dayPrediction = new Actual(repository).getDayPrediction(Currency.USD, 0);
        List<CurrencyModel> monthPrediction = new Actual(repository).getMonthPrediction(Currency.USD);
        assertThat(dayPrediction).isEqualTo(monthPrediction.get(0));
    }

    @Test
    void checks_Another_Algorithms_Not_Equals() {
        Currency usd = Currency.USD;
        CurrencyModel dayPredictionActual = new Actual(repository).getDayPrediction(usd, 0);
        CurrencyModel dayPredictionRegress = new LineurRegressionImpl(repository).getDayPrediction(usd, 0);
        CurrencyModel dayPredictionMoon = new Moon(repository).getDayPrediction(usd, 0);
        assertThat(dayPredictionActual).isNotEqualTo(dayPredictionRegress);
        assertThat(dayPredictionActual).isNotEqualTo(dayPredictionMoon);
        assertThat(dayPredictionRegress).isNotEqualTo(dayPredictionMoon);

    }

    @Test
    void checks_Actual_Week_month_Not_Equals() {
        Currency usd = Currency.USD;
        List<CurrencyModel> monthPrediction = new Actual(repository).getMonthPrediction(usd);
        List<CurrencyModel> weekPrediction = new Actual(repository).getWeekPrediction(usd);
        assertThat(monthPrediction).isNotEqualTo(weekPrediction);
    }

    @Test
    void checks_Another_Currencies_Not_Equals() {
        Currency usd = Currency.USD;
        Currency eur = Currency.EUR;
        Currency aTry = Currency.TRY;
        CurrencyModel usdPred = new Actual(repository).getDayPrediction(usd, 0);
        CurrencyModel eurPred = new Actual(repository).getDayPrediction(eur, 0);
        CurrencyModel tryPred = new Actual(repository).getDayPrediction(aTry, 0);
        assertThat(usdPred).isNotEqualTo(eurPred);
        assertThat(usdPred).isNotEqualTo(tryPred);
        assertThat(eurPred).isNotEqualTo(tryPred);
    }
}