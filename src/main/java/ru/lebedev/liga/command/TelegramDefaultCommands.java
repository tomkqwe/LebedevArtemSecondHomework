package ru.lebedev.liga.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TelegramDefaultCommands {
    private String command;
    public final static Logger LOGGER = LoggerFactory.getLogger(TelegramDefaultCommands.class);

    public TelegramDefaultCommands(String command) {
        this.command = command;
    }

    public void writeLog(){
        LOGGER.info("Обрабатываем команду {}", command);
    }
}
