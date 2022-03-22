package ru.lebedev.liga.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramPCALB.class);
    private final CurrencyRepository repository = new CurrencyRepositoryImpl();
    private static final String BOT_USERNAME_KEY = "bot.name";
    private static final String BOT_TOKEN_KEY = "bot.token";


    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOT_USERNAME_KEY);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOT_TOKEN_KEY);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            ForecastService service = new ChooseAlgorithm(text, repository).returnNeedService();
            Command command = new PickPrediction(repository, service, text).pickCommandFromMessage();

            try {
                if (CheckCorrectCommand.isValidGraph(text)) {
                    GraphPrediction graphPrediction = new GraphPrediction(service, text);
                    InputFile inputFile = new InputFile(new File(graphPrediction.returnGraphName(graphPrediction.executeGraph())));
                    execute(new SendPhoto(chatId, inputFile));
                } else {
                    execute(new SendMessage(chatId, command.commandExecute()));
                }
            } catch (TelegramApiException e) {
                LOGGER.error("error tg", e);
            }
        }
    }
}


