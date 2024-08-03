package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.TelegramUser;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String STOP_MESSAGE = "Деактивировал все ваши запланированные события.";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findByChatId(chatId);
        optionalTelegramUser.ifPresent(it -> {
            it.setActive(false);
            telegramUserService.save(it);
        });
    }
}
