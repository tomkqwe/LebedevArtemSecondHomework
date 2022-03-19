package ru.lebedev.liga.command;

import ru.lebedev.liga.command.dateCommand.CommandDateDataOptionImpl;
import ru.lebedev.liga.command.dateCommand.CommandPeriodDataOptionImpl;
import ru.lebedev.liga.command.dateCommand.DataOption;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;

import java.util.Arrays;
import java.util.Locale;


public class ChoosePrediction extends AbstractCommand implements Command {
   private DataOption dataOption;
    private static final String DATE = "-date";
    private static final String PERIOD = "-period";


    private Command someRealization;


    public ChoosePrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);

    }


    @Override
    public String commandExecute() {
        String lowerCaseCommand = super.getCommand().toLowerCase(Locale.ROOT);
        if (lowerCaseCommand.equals("/info")) {
            return new InfoCommand(lowerCaseCommand).commandExecute();
        }
        if (lowerCaseCommand.equals("/start")) {
            return new StartCommand(lowerCaseCommand).commandExecute();
        }
        if (lowerCaseCommand.equals("/contacts")) {
            return new ContactCommand(lowerCaseCommand).commandExecute();
        }
        ForecastService service = new ChooseNeedService(lowerCaseCommand, getRepository()).returnNeedService();
        String[] wordsInCommand = lowerCaseCommand.split(" ");
        if (Arrays.asList(wordsInCommand).contains(DATE)){
            someRealization = new CommandDateDataOptionImpl().returnDatePeriodWhatYouWant(getRepository(),service,super.getCommand());
        }else if (Arrays.asList(wordsInCommand).contains(PERIOD)){
            someRealization = new CommandPeriodDataOptionImpl().returnDatePeriodWhatYouWant(getRepository(),service,super.getCommand());
        }else {
            return new ErrorMessage(super.getCommand()).commandExecute();
        }

//            switch (wordsInCommand[2]) {
//                case "tomorrow" -> someRealization = new TomorrowPrediction(super.getRepository(), service, super.getCommand());
//                case "week" -> someRealization = new WeekPrediction(super.getRepository(), service, super.getCommand());
//                case "month" -> someRealization = new MonthPrediction(super.getRepository(), service, super.getCommand());
//                default -> {
//                    if (wordsInCommand[2].matches("[0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}") && wordsInCommand[3].matches("((moon)|(actual)|(regress))")) {
//                        someRealization = new ConcretDate(super.getRepository(), service, super.getCommand());
//                    } else {
//                        return errorCommand;
//                    }
//                }
//
//            }

        return someRealization.commandExecute();
    }

}
