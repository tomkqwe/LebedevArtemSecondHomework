package ru.lebedev.liga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChooseNeedServiceTest {
    @Mock
    CurrencyRepository repository = new CurrencyRepositoryImpl();


    @Test
    void return_True_Check_Moon_Instance() {
        ChooseNeedService chooseNeedService = new ChooseNeedService("rate USD week moon list", repository);
        assertThat(chooseNeedService.returnNeedService()).isExactlyInstanceOf(Moon.class);
    }
    @Test
    void return_True_Check_Actual_Instance() {
        ChooseNeedService chooseNeedService = new ChooseNeedService("rate USD week actual list", repository);
        assertThat(chooseNeedService.returnNeedService()).isExactlyInstanceOf(Actual.class);
    }
    @Test
    void return_True_Check_Regress_Instance() {
        ChooseNeedService chooseNeedService = new ChooseNeedService("rate USD week regress list", repository);
        assertThat(chooseNeedService.returnNeedService()).isExactlyInstanceOf(LineurRegressionImpl.class);
    }
    @Test
    void null_Service_Check() {
        ChooseNeedService chooseNeedService = new ChooseNeedService("sqsqs qwewe weqweqweek sqsq liqweqwst", repository);
        ForecastService service = chooseNeedService.returnNeedService();
        assertThat(service).isNull();

    }

}