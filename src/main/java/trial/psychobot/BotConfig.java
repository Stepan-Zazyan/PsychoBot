package trial.psychobot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public CommandLineRunner startBot(PsychoBotLogic psychoBotLogic) {
        return args -> {
            try {
                // Регистрация бота с использованием DefaultBotSession
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(psychoBotLogic);
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        };
    }
}
