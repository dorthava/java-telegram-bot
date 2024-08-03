package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.TelegramUser;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class StartCommand implements Command {
    public final String START_MESSAGE = "Привет. Я Telegram Bot. Я помогу тебе в планировке твоих задач на ближайшее будущее. " +
            "Я еще маленький и только учусь.";
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findByChatId(chatId);
        TelegramUser telegramUser;
        if(optionalTelegramUser.isEmpty()) {
            telegramUser = new TelegramUser();
            telegramUser.setActive(true);
            telegramUser.setChatId(chatId);
        } else {
            telegramUser = optionalTelegramUser.get();
            telegramUser.setActive(true);
        }
        telegramUserService.save(telegramUser);
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
