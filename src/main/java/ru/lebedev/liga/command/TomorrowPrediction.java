package ru.lebedev.liga.command;

import ru.lebedev.liga.command.dateCommand.DataOption;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;


public class TomorrowPrediction extends AbstractCommand implements Command {
    public TomorrowPrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);
    }

    @Override
    public String commandExecute() {
        if (super.isCorrectCommand(super.getCommand())) {
            CurrencyModel dayPrediction = super.getDayPrediction();
            return super.correctOutput(dayPrediction);
        }
        return super.writeMessage();
    }

}

