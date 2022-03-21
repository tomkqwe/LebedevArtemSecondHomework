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
        strings.add("Доступные валюты:"+SMILE_MONEY_FACE+ collect);
        strings.add("Доступные алгоритмы " +SMILE_FAX+":\"Актуальный\" - actual, \"Мистический\"- moon, \"Линейной регрессии\"- regress");
        strings.add("Доступные периоды времени"+SMILE_TIME+": месяц -month, неделя -week");
        strings.add("Доступные даты прогноза"+SMILE_TIME+": -date завтра tomorrow, любая дата из будущего в формате дд.мм.гггг");
        strings.add("введите команду вида:"+SMILE_USD+" rate TRY -date tomorrow -alg moon \n для прогноза на завтра");
        strings.add("введите команду вида:"+SMILE_EUR+" rate EUR -date 22.02.2030 -alg moon \n для прогноза на точную дату из будущего");
        strings.add("введите команду вида: "+SMILE_USD+" rate USD -period week -alg moon -output list \n для прогноза на неделю или месяц с выводом в список");
        strings.add("введите команду вида: "+SMILE_EUR+" rate USD,TRY -period month -alg moon -output graph \n для прогноза на неделю или месяц с выводом как картинка-график"+SMILE_GRAPH);
        return String.join("\n", strings);
    }
}
