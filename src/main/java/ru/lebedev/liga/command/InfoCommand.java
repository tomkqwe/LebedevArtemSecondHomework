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
        strings.add("��������� ������:"+SMILE_MONEY_FACE+ collect);
        strings.add("��������� ��������� " +SMILE_FAX+":\"����������\" - actual, \"�����������\"- moon, \"�������� ���������\"- regress");
        strings.add("��������� ������� �������"+SMILE_TIME+": ����� -month, ������ -week");
        strings.add("��������� ���� ��������"+SMILE_TIME+": -date ������ tomorrow, ����� ���� �� �������� � ������� ��.��.����");
        strings.add("������� ������� ����:"+SMILE_USD+" rate TRY -date tomorrow -alg moon \n ��� �������� �� ������");
        strings.add("������� ������� ����:"+SMILE_EUR+" rate EUR -date 22.02.2030 -alg moon \n ��� �������� �� ������ ���� �� ��������");
        strings.add("������� ������� ����: "+SMILE_USD+" rate USD -period week -alg moon -output list \n ��� �������� �� ������ ��� ����� � ������� � ������");
        strings.add("������� ������� ����: "+SMILE_EUR+" rate USD,TRY -period month -alg moon -output graph \n ��� �������� �� ������ ��� ����� � ������� ��� ��������-������"+SMILE_GRAPH);
        return String.join("\n", strings);
    }
}
