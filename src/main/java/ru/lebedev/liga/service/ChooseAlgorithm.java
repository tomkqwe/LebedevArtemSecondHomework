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
//        String[] commands = command.split(" ");
//        String algoToSwitchCase = commands[commands.length - 1];
//        if (algoToSwitchCase.matches("(list)|(graph)")) {
//            algoToSwitchCase = commands[commands.length - 2];
//        }
//        switch (algoToSwitchCase) {
//            case "moon" -> service = new MoonAlgorithm(repository);
//            case "regress" -> service = new LineurRegressionImpl(repository);
//            case "actual" -> service = new Actual(repository);
//        }
        service = new CommandslAlgOptionImpl().chooseAlg(repository, command);
        return service;
    }
}
