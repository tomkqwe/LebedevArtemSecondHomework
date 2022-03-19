package ru.lebedev.liga.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TelegramDefaultCommands {
    private String command;

    public TelegramDefaultCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
