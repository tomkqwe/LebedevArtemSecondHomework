package ru.lebedev.liga;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.lebedev.liga.command.ChoosePrediction;
import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.command.ConcretDate;
import ru.lebedev.liga.command.dateCommand.DataOption;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.view.TelegramPCALB;

import java.io.File;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Приложение стартовало!");

        String command = "rate USD -date 23.05.2034 -alg moon";
        CurrencyRepository repository = new CurrencyRepositoryImpl();
        ForecastService service = new ChooseNeedService(command,repository).returnNeedService();
        String execute = new ChoosePrediction(repository, service, command).commandExecute();
        System.out.println(execute);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramPCALB telegramPCALB = new TelegramPCALB();
            telegramBotsApi.registerBot(telegramPCALB);

        } catch (TelegramApiException e) {
            LOGGER.error("Ошибка Телеграмм бота", e);
        }

    }
}
