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
        Currency currency = Currency.valueOf(curryncies);

        return currency;
    }


    public String correctOutput(CurrencyModel dayPrediction) {
        return String.format("%s \uD83D\uDD70 - %s \uD83D\uDCB8", dayPrediction.getDate().format(DataUtil.OUTPUT_FORMATTER),
                dayPrediction.getValue().multiply(dayPrediction.getNominal()).setScale(2, RoundingMode.FLOOR));
    }

    public String writeMessage() {
        String s = "Вы ввели: " + command + " -это неверная команда\n Введите /info для справки";
        return s;
    }

}
