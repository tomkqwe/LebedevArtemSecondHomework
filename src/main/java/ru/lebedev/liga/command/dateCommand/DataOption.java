package ru.lebedev.liga.command.dateCommand;

import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.ForecastService;

public interface DataOption {

    Command returnDatePeriodWhatYouWant(CurrencyRepository repository, ForecastService service, String command);
}
