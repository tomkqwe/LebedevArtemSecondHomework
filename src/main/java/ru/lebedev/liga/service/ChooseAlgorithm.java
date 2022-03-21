package ru.lebedev.liga.service;

import ru.lebedev.liga.command.algCommand.CommandslAlgOptionImpl;
import ru.lebedev.liga.repository.CurrencyRepository;

public class ChooseAlgorithm {
    private final String command;
    private final CurrencyRepository repository;
    private ForecastService service;

    public ChooseAlgorithm(String command, CurrencyRepository repository) {
        this.command = command;
        this.repository = repository;
    }

    public ForecastService returnNeedService() {
        service = new CommandslAlgOptionImpl().chooseAlg(repository, command);
        return service;
    }
}
