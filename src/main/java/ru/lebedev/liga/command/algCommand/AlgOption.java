package ru.lebedev.liga.command.algCommand;

import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;

public interface AlgOption {

    ForecastService chooseAlg(CurrencyRepository repository,String command);
}
