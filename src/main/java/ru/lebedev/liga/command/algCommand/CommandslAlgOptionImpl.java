package ru.lebedev.liga.command.algCommand;

import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.service.Actual;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.service.LineurRegressionImpl;
import ru.lebedev.liga.service.MoonAlgorithm;

import java.util.Arrays;

public class CommandslAlgOptionImpl implements AlgOption {
    public final static String ACTUAL = "actual";
    public final static String REGRESS = "regress";
    public final static String MISTIC_OR_MOON = "moon";


    @Override
    public ForecastService chooseAlg(CurrencyRepository repository, String command) {
        String[] wordsInCommand = command.toLowerCase().split(" ");
        if (Arrays.asList(wordsInCommand).contains(ACTUAL) && Arrays.asList(wordsInCommand).contains(ARGUMENT)) {
            return new Actual(repository);
        }
        if (Arrays.asList(wordsInCommand).contains(ARGUMENT) && Arrays.asList(wordsInCommand).contains(REGRESS)) {
            return new LineurRegressionImpl(repository);
        }
        if (Arrays.asList(wordsInCommand).contains(ARGUMENT) && Arrays.asList(wordsInCommand).contains(MISTIC_OR_MOON)) {
            return new MoonAlgorithm(repository);
        }
        return null;
    }
}
