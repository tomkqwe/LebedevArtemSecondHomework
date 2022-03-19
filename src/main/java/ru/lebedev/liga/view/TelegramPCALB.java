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

import java.io.File;
import java.io.IOException;

public class TelegramPCALB extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override
    public String getBotUsername() {
        return "Artem_LebedevPCH2_bot";
    }

    @Override
    public String getBotToken() {
        return "5120101031:AAGmBWPNUuZY-n7Yrdh9-bPn3QT3GWRDH-c";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        LOGGER.info("Запускается функция onUpdateReceived, которая обрабатывает мессаж от юзера");
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && update.getMessage().hasText()) {
            LOGGER.debug("Создаётся объект репозитория, где хранится вся информация в списках");
            CurrencyRepository repository = new CurrencyRepositoryImpl();
            String text = update.getMessage().getText();
            LOGGER.debug("Получаем сообщение: {}", text);
            LOGGER.debug("Выбираем алгоритм исходя из сообщения");
            ForecastService service = new ChooseNeedService(text, repository).returnNeedService();
            LOGGER.debug("Выбираем команду, которая обработает сообщение и вернет прогноз");
            Command command = new ChoosePrediction(repository, service, text);
            InputFile inputFile = new InputFile(new File("./src/main/resources/graphics.png"));
            SendPhoto sendPhoto = new SendPhoto(chatId, inputFile);
            String[] splitCommand = text.split(" ");
            String checkGraphOrNot = splitCommand[splitCommand.length - 1];
            try {
                LOGGER.debug("Отправляем сообщение пользователю");

                if (checkGraphOrNot.equals("graph") && splitCommand.length == 5) {
                    GraphPrediction graphPrediction = new GraphPrediction(repository, service, text);
                    if (graphPrediction.isCorrectCommand(text)) {
                        execute(new SendMessage(chatId, command.commandExecute()));
                    } else {
                        graphPrediction.commandExecute();
                        execute(sendPhoto);
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


