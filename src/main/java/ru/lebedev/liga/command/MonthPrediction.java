package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;
import ru.lebedev.liga.model.CurrencyModel;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MonthPrediction extends AbstractCommand implements Command {
    public MonthPrediction(CurrencyRepository repository, ForecastService service, String command) {
        super(repository, service, command);
    }

    @Override
    public String commandExecute() {
        if (CheckCorrectCommand.isValidCommand(super.getCommand())) {
            return getListWithMonthPredictions()
                    .stream()
                    .map(super::correctOutput)
                    .collect(Collectors.joining("\n"));
        }
        return getErrorCommand();
    }

    private List<CurrencyModel> getListWithMonthPredictions() {
        ForecastService service = super.getService();
        return service.getMonthPrediction(super.getCurrency());

    }


    public List<BigDecimal> getMonthPrediction(Currency currency) {

        return super.getService()
                .getMonthPrediction(currency)
                .stream()
                .map(CurrencyModel::getValue)
                .collect(Collectors.toList());
    }

}
