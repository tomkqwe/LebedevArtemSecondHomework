package ru.lebedev.liga.command;

public class ContactCommand extends TelegramDefaultCommands implements Command {

    public ContactCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        writeLog();
        return "По всем вопросам обращаться к @tomkqwe \uD83E\uDD17";

    }
}
