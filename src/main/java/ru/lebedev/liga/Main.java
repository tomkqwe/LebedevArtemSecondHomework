package ru.lebedev.liga;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.lebedev.liga.view.TelegramPCALB;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Приложение стартовало!");

//        String command = "rate USD -period week -alg moon -output graph";
//        CurrencyRepository repository = new CurrencyRepositoryImpl();
//        ForecastService service = new ChooseAlgorithm(command,repository).returnNeedService();
//        PickPrediction pickPrediction = new PickPrediction(repository, service, command);
//        String execute = pickPrediction.pickCommandFromMessage().commandExecute();
//        System.out.println(execute);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramPCALB telegramPCALB = new TelegramPCALB();
            telegramBotsApi.registerBot(telegramPCALB);

        } catch (TelegramApiException e) {
            LOGGER.error("Ошибка Телеграмм бота", e);
        }

    }
}
