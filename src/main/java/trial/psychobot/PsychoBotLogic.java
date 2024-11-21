package trial.psychobot;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class PsychoBotLogic extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    int count = 0;

    String botReply = "";

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String userMessage = message.getText().toLowerCase();
            log.info("Received message: {}", userMessage);

            botReply = generateReply(userMessage);
            log.info("Bot reply: {}", botReply);

            SendMessage response = new SendMessage();
            response.setChatId(message.getChatId().toString());
            response.setText(botReply);

            try {
                execute(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generateReply(String userMessage) {

        // Приводим к нижнему регистру для игнорирования регистра
        String lowerMessage = userMessage.toLowerCase();
        if (lowerMessage.contains("почему") || lowerMessage.contains("когда")
                || lowerMessage.contains("где") || lowerMessage.contains("зачем")
                || lowerMessage.contains("остановись") || lowerMessage.contains("хватит")
                || lowerMessage.contains("стоп") || lowerMessage.contains("прекрати")
                || lowerMessage.contains("тебя") || lowerMessage.contains("что делать")
                || lowerMessage.contains("как быть") || lowerMessage.contains("как остановиться")
                || lowerMessage.contains("помоги") || lowerMessage.contains("подскажи")
                || lowerMessage.contains("что дальше") || lowerMessage.contains("чем помочь")
                || lowerMessage.contains("когда закончится") || lowerMessage.contains("что со мной")
                || lowerMessage.contains("помогите") || lowerMessage.contains("почему так")) {
            return "а чё?";

        } else if (lowerMessage.contains("чё") || lowerMessage.contains("что")) {
            return "ничё";

        } else if (lowerMessage.matches(".*(ешь|ала|ала$|ел$|ела|ешь$|ю$|ую$|им$|ит$|ил$|или$|ся)$") || lowerMessage.contains("я ") || lowerMessage.contains("меня")) {// Проверка на окончание на "ешь"
            return "ну и чё?";

        } else {
            count++;
            if (count % 3 == 0) {
                count = 0;
                return "а чё?";
            }
            return "чё?";
        }
    }

}
