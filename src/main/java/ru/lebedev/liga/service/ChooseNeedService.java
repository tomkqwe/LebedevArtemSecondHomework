package ru.lebedev.liga.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import ru.lebedev.liga.repository.CurrencyRepository;

public class ChooseNeedService {
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(ChooseNeedService.class);
    private final String command;
    private final CurrencyRepository repository;
    private ForecastService service;

    public ChooseNeedService(String command, CurrencyRepository repository) {
        this.command = command;
        this.repository = repository;
    }

    public ForecastService returnNeedService() {
        LOGGER.debug("Выбираем нужный алгоритм исходя из полученной комманды: {}", command);
        String[] commands = command.split(" ");
        String algoToSwitchCase = commands[commands.length - 1];
        if (algoToSwitchCase.matches("(list)|(graph)")) {
            algoToSwitchCase = commands[commands.length - 2];
        }
        switch (algoToSwitchCase) {
            case "moon" -> service = new Moon(repository);
            case "regress" -> service = new LineurRegressionImpl(repository);
            case "actual" -> service = new Actual(repository);
        }
        return service;
    }
}
