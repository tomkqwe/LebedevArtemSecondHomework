package ru.lebedev.liga.command;

import ru.lebedev.liga.view.TelegramPCALB;

public class StartCommand extends TelegramDefaultCommands implements Command {
    public StartCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        String botUsername = new TelegramPCALB().getBotUsername();
       return  "������! "+ SMILE_HI+ " ���� ����� "+ botUsername+" ��� ���� ��� �� ������ ��� � ���� ����� /info "+SMILE_WINK;

    }
}
