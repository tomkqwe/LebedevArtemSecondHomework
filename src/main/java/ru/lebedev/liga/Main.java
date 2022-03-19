package ru.lebedev.liga;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.lebedev.liga.view.TelegramPCALB;

import java.io.File;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Приложение стартовало!");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramPCALB telegramPCALB = new TelegramPCALB();
            telegramBotsApi.registerBot(telegramPCALB);

        } catch (TelegramApiException e) {
            LOGGER.error("Ошибка Телеграмм бота", e);
        }

    }
}
