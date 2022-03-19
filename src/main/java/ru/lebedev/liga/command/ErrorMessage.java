package ru.lebedev.liga.command;

public class ErrorMessage extends TelegramDefaultCommands implements Command{
    public ErrorMessage(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        return "Вы ввели: " + super.getCommand()+ " -это неверная команда\n Введите /info для справки";
    }
}
