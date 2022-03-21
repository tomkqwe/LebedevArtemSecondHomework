package ru.lebedev.liga.view;

import com.github.sh0nk.matplotlib4j.Plot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.lebedev.liga.Main;
import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.command.GraphPrediction;
import ru.lebedev.liga.command.PickPrediction;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseAlgorithm;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.PropertiesUtil;
import ru.lebedev.liga.validate.CheckCorrectCommand;

import java.io.File;

public class TelegramPCALB extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String BOTUSERNAME_KEY = "bot.name";
    private static final String BOTTOKEN_KEY = "bot.token";


    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOTUSERNAME_KEY);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOTTOKEN_KEY);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && update.getMessage().hasText()) {
            CurrencyRepository repository = new CurrencyRepositoryImpl();
            String text = update.getMessage().getText();
            ForecastService service = new ChooseAlgorithm(text, repository).returnNeedService();
            Command command = new PickPrediction(repository, service, text).pickCommandFromMessage();

            try {
                if (CheckCorrectCommand.isValidGraph(text)) {
                    GraphPrediction graphPrediction = new GraphPrediction(repository, service, text);
                    InputFile inputFile = new InputFile(new File(graphPrediction.returnGraphName(graphPrediction.executeGraph())));
                    execute(new SendPhoto(chatId,inputFile));
                } else {
                    execute(new SendMessage(chatId, command.commandExecute()));
                }
            } catch (TelegramApiException e) {
                LOGGER.error("error tg",e);
            }
        }
    }
}


