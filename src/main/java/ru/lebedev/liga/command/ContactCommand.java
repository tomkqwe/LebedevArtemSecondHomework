package ru.lebedev.liga.command;

public class ContactCommand extends TelegramDefaultCommands implements Command {

    public ContactCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        return "�� ���� �������� ���������� � @tomkqwe"+SMILE_WITH_HANDS;

    }
}
