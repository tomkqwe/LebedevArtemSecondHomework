package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class WeekPrediction extends AbstractCommand implements Command {

    public WeekPrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);
    }

    @Override
    public String commandExecute() {
        if (CheckCorrectCommand.isValidCommand(super.getCommand())) {
            return getListWithWeekPredictions()
                    .stream()
                    .map(super::correctOutput)
                    .collect(Collectors.joining("\n"));
        }
        return getErrorCommand();
    }

    private List<CurrencyModel> getListWithWeekPredictions() {
//        ForecastService service = new ChooseNeedService(super.getCommand(), super.getRepository()).returnNeedService();
        ForecastService service = super.getService();
        return service.getWeekPrediction(super.getCurrency());
    }

    public List<BigDecimal> getWeekPrediction(Currency currency) {
        return super.getService()
                .getWeekPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }

}
