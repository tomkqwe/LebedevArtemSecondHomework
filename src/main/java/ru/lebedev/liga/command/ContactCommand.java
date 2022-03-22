package ru.lebedev.liga.command;

public class ContactCommand extends TelegramDefaultCommands implements Command {

    public ContactCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        return "По всем вопросам обращаться к @tomkqwe"+SMILE_WITH_HANDS;

    }
}
