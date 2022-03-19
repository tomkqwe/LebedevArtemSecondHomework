package ru.lebedev.liga.command;

import ru.lebedev.liga.view.TelegramPCALB;

public class StartCommand extends TelegramDefaultCommands implements Command {
    public StartCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        String botUsername = new TelegramPCALB().getBotUsername();
       return  "Привет! \uD83D\uDC4B меня зовут "+ botUsername+" для того что бы узнать что я умею нажми /info \uD83D\uDE09";

    }
}
