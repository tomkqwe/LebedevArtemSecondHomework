package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseAlgorithm;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.DataUtil;

import java.math.RoundingMode;

public abstract class AbstractCommand {
    public final static int CURRENCY_INDEX = 1;
    private final CurrencyRepository repository;
    private final String command;
    private ForecastService service;
    private final static String SMILE_CLOCK = "\uD83D\uDD70";
    private final static String SMILE_CASH = "\uD83D\uDCB8";

    public AbstractCommand(CurrencyRepository repository, ForecastService service, String command) {
        this.repository = repository;
        this.service = service;
        this.command = command;
    }

    public String getErrorCommand() {
        return new ErrorMessage(command).commandExecute();
    }

    public CurrencyRepository getRepository() {
        return repository;
    }

    public ForecastService getService() {
        return service;
    }

    public String getCommand() {
        return command.toLowerCase();
    }

    public CurrencyModel getDayPrediction() {
        service = new ChooseAlgorithm(command, repository).returnNeedService();
        Currency currency = getCurrency();
        return service.getDayPrediction(currency, 0);
    }

    public Currency getCurrency() {
        String[] commands = command.split(" ");
        String curryncies = commands[CURRENCY_INDEX].toUpperCase();
        return Currency.valueOf(curryncies);
    }


    public String correctOutput(CurrencyModel dayPrediction) {
        return String.format("%s - "+SMILE_CLOCK+" %s "+SMILE_CASH, dayPrediction.getDate().format(DataUtil.OUTPUT_FORMATTER),
                dayPrediction.getValue().multiply(dayPrediction.getNominal()).setScale(2, RoundingMode.FLOOR));
    }


}
