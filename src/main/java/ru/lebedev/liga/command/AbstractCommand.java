package ru.lebedev.liga.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.*;
import ru.lebedev.liga.utils.DataUtil;

import java.math.RoundingMode;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCommand {
    private final CurrencyRepository repository;
    private  ForecastService service;
    private final String command;
    private final static Pattern PATTERN =
            Pattern.compile("rate ((usd)|(eur)|(try)|(bgn)|(amd)) ((week)|(tomorrow)|(month)|([0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4})) ((actual)|(moon)|(regress)) ?((list)|(graph))?");
    public final static Logger LOGGER = LoggerFactory.getLogger(AbstractCommand.class);

    public AbstractCommand(CurrencyRepository repository, ForecastService service, String command) {
        this.repository = repository;
        this.service = service;
        this.command = command;
    }

    public CurrencyRepository getRepository() {
        return repository;
    }

    public ForecastService getService() {
        return service;
    }

    public String getCommand() {
        return command.toLowerCase(Locale.ROOT);
    }


    public boolean isCorrectCommand(String command){
        LOGGER.debug("ѕровер€ем что комманда соответствует шаблону");
        Matcher matcher = PATTERN.matcher(command.toLowerCase(Locale.ROOT));
        return matcher.find();
    }


    public CurrencyModel getDayPrediction() {
        service = new ChooseNeedService(command,repository).returnNeedService();
        Currency currency = getCurrency();
        CurrencyModel dayPrediction = service.getDayPrediction(currency, 0);
        LOGGER.debug("AbstractCommand метод getDayPrediction прогноз на день: {}",dayPrediction.toString());
        return dayPrediction;
    }

    public Currency getCurrency() {
        String[] commands = command.split(" ");
        Currency currency = Currency.valueOf(commands[1].toUpperCase(Locale.ROOT));
        LOGGER.debug("AbstractCommand метод getCurrency получаем валюту: {}",currency);
        return currency;
    }


    public String correctOutput(CurrencyModel dayPrediction) {
        LOGGER.debug("AbstractCommand метод correctOutput форматируем вывод");

        return  String.format("%s \uD83D\uDD70 - %s \uD83D\uDCB8", dayPrediction.getDate().format(DataUtil.OUTPUT_FORMATTER),
                dayPrediction.getValue().multiply(dayPrediction.getNominal()).setScale(2, RoundingMode.FLOOR));
    }

    public String writeMessage(){
        String s = "¬ы ввели: " + command + " -это неверна€ команда\n ¬ведите /info дл€ справки";
        LOGGER.debug("AbstractCommand метод writeMessage выводим сообщение о не корректных данных:\n{}",s);
        return s;
    }

}
