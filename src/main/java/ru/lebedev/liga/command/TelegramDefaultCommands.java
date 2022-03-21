package ru.lebedev.liga.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TelegramDefaultCommands {
    public static final String SMILE_WITH_HANDS = "\uD83E\uDD17";
    public static final String SMILE_HI = "\uD83D\uDC4B";
    public static final String SMILE_WINK = "\uD83D\uDE09";
    public static final String SMILE_MONEY_FACE = "\uD83E\uDD11";
    public static final String SMILE_FAX = "\uD83D\uDCE0";
    public static final String SMILE_USD = "\uD83D\uDCB5";
    public static final String SMILE_EUR = "\uD83D\uDCB6";
    public static final String SMILE_GRAPH = "\uD83D\uDCC8";
    public static final String SMILE_VERY_BAD = "\uD83E\uDD2C";
    public static final String SMILE_TIME = "\uD83D\uDD70";


    private String command;

    public TelegramDefaultCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
