package ru.lebedev.liga.view;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.lebedev.liga.Main;
import ru.lebedev.liga.command.ChoosePrediction;
import ru.lebedev.liga.command.Command;
import ru.lebedev.liga.command.GraphPrediction;
import ru.lebedev.liga.repository.CurrencyRepository;
import ru.lebedev.liga.repository.CurrencyRepositoryImpl;
import ru.lebedev.liga.service.ChooseNeedService;
import ru.lebedev.liga.service.ForecastService;
import ru.lebedev.liga.utils.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
        LOGGER.info("Запускается функция onUpdateReceived, которая обрабатывает мессаж от юзера");
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && update.getMessage().hasText()) {
            CurrencyRepository repository = new CurrencyRepositoryImpl();
            String text = update.getMessage().getText();
            LOGGER.debug("Получаем сообщение: {}", text);
            ForecastService service = new ChooseNeedService(text, repository).returnNeedService();
            Command command = new ChoosePrediction(repository, service, text);
            String[] splitCommand = text.split(" ");
            String checkGraphOrNot = splitCommand[splitCommand.length - 1];
            try {
                if (checkGraphOrNot.equals("graph") && splitCommand.length == 5) {
                    GraphPrediction graphPrediction = new GraphPrediction(repository, service, text);
                    if (graphPrediction.isCorrectCommand(text)) {
                        execute(new SendMessage(chatId, command.commandExecute()));
                    } else {
                        graphPrediction.commandExecute();
                        File file = new File(getClass().getClassLoader().getResource("graphics.png").getFile());
                        execute(new SendPhoto(chatId, new InputFile(file)));
                    }
                } else {
                    execute(new SendMessage(chatId, command.commandExecute()));
                }
            } catch (TelegramApiException | PythonExecutionException | IOException e) {
                LOGGER.error("Возникла ошибка", e.getCause());
            }
        }
    }
}


