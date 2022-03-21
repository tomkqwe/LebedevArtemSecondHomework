package ru.lebedev.liga.command;

import ru.lebedev.liga.command.dateCommand.CommandDateDataOptionImpl;
import ru.lebedev.liga.command.dateCommand.CommandPeriodDataOptionImpl;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.util.Arrays;
import java.util.List;

public class PickPrediction {
    private final static String INFO = "/info";
    private final static String START = "/start";
    private final static String CONTACTS = "/contacts";
    private static final String DATE = "-date";
    private static final String PERIOD = "-period";
    private final CurrencyRepository repository;
    private final ForecastService service;
    private final String messageFromUser;

    public PickPrediction(CurrencyRepository repository, ForecastService service, String messageFromUser) {
        this.repository = repository;
        this.service = service;
        this.messageFromUser = messageFromUser;
    }

  public  Command pickCommandFromMessage() {
        if (messageFromUser.equalsIgnoreCase(INFO)) {
            return new InfoCommand(messageFromUser);
        }
        if (messageFromUser.equalsIgnoreCase(START)) {
            return new StartCommand(messageFromUser);
        }
        if (messageFromUser.equalsIgnoreCase(CONTACTS)) {
            return new ContactCommand(messageFromUser);
        }
        List<String> wordsInMessage;
        try {
            wordsInMessage = Arrays.asList(messageFromUser.toLowerCase().split(" "));
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ErrorMessage(messageFromUser);
        }
        if (wordsInMessage.contains(DATE)) {
            return new CommandDateDataOptionImpl().returnDatePeriodWhatYouWant(repository, service, messageFromUser);
        }
        if (wordsInMessage.contains(PERIOD) && CheckCorrectCommand.isValidCommand(messageFromUser)) {
            return new CommandPeriodDataOptionImpl().returnDatePeriodWhatYouWant(repository, service, messageFromUser);
        }
        if (wordsInMessage.contains(PERIOD) && CheckCorrectCommand.isValidGraph(messageFromUser)){
            return new GraphPrediction(repository,service,messageFromUser);
        }

        return new ErrorMessage(messageFromUser);
    }


}
