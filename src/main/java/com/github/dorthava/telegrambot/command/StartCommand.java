package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    public final String START_MESSAGE = "Привет. Я Telegram Bot. Я помогу тебе быть в курсе последних статей тех авторов, " +
            "котрые тебе интересны. Я еще маленький и только учусь.";
    private final SendBotMessageService sendBotMessageService;

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
