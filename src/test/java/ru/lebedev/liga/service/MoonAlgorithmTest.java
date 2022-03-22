package ru.lebedev.liga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MoonAlgorithmTest {
    @Mock
    CurrencyRepository repository = new CurrencyRepositoryImpl();
    Currency eur = Currency.EUR;

    @Test
    void check_Day_Prediction_Need_to_Be_Tomorrow() {
        LocalDate date = new MoonAlgorithm(repository).getDayPrediction(eur,0).getDate();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        assertThat(date).isEqualTo(tomorrow);
    }

    @Test
    void check_Weeks_Predictions_First_Elements_Equals_Rest_Are_Not() {
        ArrayList<CurrencyModel> firstList = new MoonAlgorithm(repository).getWeekPrediction(eur);
        ArrayList<CurrencyModel> secondList = new MoonAlgorithm(repository).getWeekPrediction(eur);
        assertThat(firstList.get(0)).isEqualTo(secondList.get(0));
        assertThat(firstList).isNotEqualTo(secondList);
    }

    @Test
    void checks_Dates_In_Month_Predictions() {
        List<CurrencyModel> monthPrediction = new MoonAlgorithm(repository).getMonthPrediction(eur);
        List<LocalDate> localDates = monthPrediction
                .stream()
                .map(CurrencyModel::getDate)
                .collect(Collectors.toList());
        ArrayList<LocalDate> testDates = new ArrayList<>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        for (int i = 0; i < 30; i++) {
            testDates.add(tomorrow.plusDays(i));
        }
        assertThat(localDates).isEqualTo(testDates);
    }
}