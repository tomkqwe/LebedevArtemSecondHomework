package ru.lebedev.liga.command;

import ru.lebedev.liga.model.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InfoCommand extends TelegramDefaultCommands implements Command {

    public InfoCommand(String command) {
        super(command);
    }

    @Override
    public String commandExecute() {
        ArrayList<String> strings = new ArrayList<>();
        Currency[] values = Currency.values();
        String collect = Arrays
                .stream(values)
                .map(Enum::toString)
                .collect(Collectors
                        .joining(","));
        strings.add("��������� ������: \uD83E\uDD11\uD83E\uDD11\uD83E\uDD11" + collect);
        strings.add("��������� ��������� \uD83D\uDCE0\uD83D\uDCE0\uD83D\uDCE0: \"����������\" - actual, \"�����������\"- moon, \"�������� ���������\"- regress");
        strings.add("��������� ������� ������� \uD83D\uDD70\uD83D\uDD70\uD83D\uDD70: �����- month, ������ - week");
        strings.add("������� ������� ���� \uD83D\uDCB5\uD83D\uDCB4\uD83D\uDCB6\uD83D\uDCB7: rate TRY tomorrow moon \n ��� �������� �� ������");
        strings.add("������� ������� ���� \uD83D\uDCB5\uD83D\uDCB4\uD83D\uDCB6\uD83D\uDCB7: rate TRY 22.02.2030 moon \n ��� �������� �� ������ ���� �� ��������");
        strings.add("������� ������� ���� \uD83D\uDCB5\uD83D\uDCB4\uD83D\uDCB6\uD83D\uDCB7: rate USD week(month) moon list \n ��� �������� �� ������ ��� �����");
        strings.add("������� ������� ���� \uD83D\uDCB5\uD83D\uDCB4\uD83D\uDCB6\uD83D\uDCB7: rate USD,TRY month moon graph \n ��� �������� �� ������ ��� ����� � ������� ��� ��������-������ \uD83D\uDCC8\uD83D\uDCC8\uD83D\uDCC8");
        return String.join("\n", strings);
    }
}
