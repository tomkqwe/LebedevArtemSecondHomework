package ru.lebedev.liga.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;

class ChooseAlgorithmTest {
    @Mock
    CurrencyRepository repository = new CurrencyRepositoryImpl();


    @Test
    void return_True_Check_Moon_Instance() {
        ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("rate USD -period week -alg moon -output list", repository);
        assertThat(chooseAlgorithm.returnNeedService()).isExactlyInstanceOf(MoonAlgorithm.class);
    }
    @Test
    void return_True_Check_Actual_Instance() {
        ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("rate USD -period week -alg actual -output list", repository);
        assertThat(chooseAlgorithm.returnNeedService()).isExactlyInstanceOf(Actual.class);
    }
    @Test
    void return_True_Check_Regress_Instance() {
        ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("rate USD -period week -alg regress -output list", repository);
        assertThat(chooseAlgorithm.returnNeedService()).isExactlyInstanceOf(LineurRegressionImpl.class);
    }
    @Test
    void null_Service_Check() {
        ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("sqsqs qwewe weqweqweek sqsq liqweqwst", repository);
        ForecastService service = chooseAlgorithm.returnNeedService();
        assertThat(service).isNull();

    }

}