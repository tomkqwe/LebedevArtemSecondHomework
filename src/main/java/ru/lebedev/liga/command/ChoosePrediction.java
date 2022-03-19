package ru.lebedev.liga.command;

import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;

import java.util.Locale;


public class ChoosePrediction extends AbstractCommand implements Command {

    private Command someRealization;


    public ChoosePrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);

    }

    @Override
    public String commandExecute() {
        LOGGER.info("ChoosePrediction метод commandExecute , выбираем реализацию для вывода исходя из комманды: {}",super.getCommand());
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
        String[] split = lowerCaseCommand.split(" ");
        String errorCommand = super.writeMessage();
        ForecastService service = new ChooseNeedService(lowerCaseCommand, getRepository()).returnNeedService();
        try {
            switch (split[2]) {
                case "tomorrow" -> someRealization = new TomorrowPrediction(super.getRepository(), service, super.getCommand());
                case "week" -> someRealization = new WeekPrediction(super.getRepository(), service, super.getCommand());
                case "month" -> someRealization = new MonthPrediction(super.getRepository(), service, super.getCommand());
                default -> {
                    if (split[2].matches("[0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}") && split[3].matches("((moon)|(actual)|(regress))")) {
                        someRealization = new ConcretDate(super.getRepository(), service, super.getCommand());
                    } else {
                        return errorCommand;
                    }
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return super.writeMessage();
        }
        return someRealization.commandExecute();
    }

}
