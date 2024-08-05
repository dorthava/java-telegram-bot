package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.State;
import com.github.dorthava.telegrambot.models.TelegramUser;
import com.github.dorthava.telegrambot.models.UserState;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.TelegramUserService;
import com.github.dorthava.telegrambot.service.UserStateService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class StartCommand implements Command {
    public final String START_MESSAGE = "Привет. Я Telegram Bot. Я помогу тебе в планировке твоих задач на ближайшее будущее. " +
            "Я еще маленький и только учусь.";
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final UserStateService userStateService;
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, UserStateService userStateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.userStateService = userStateService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findByChatId(chatId);
        TelegramUser telegramUser;
        UserState userState;
        if(optionalTelegramUser.isEmpty()) {
            telegramUser = new TelegramUser();
            telegramUser.setActive(true);
            telegramUser.setChatId(chatId);
            userState = new UserState();
            userState.setChatId(chatId);
            userState.setState(State.WAITING_FOR_COMMAND.ordinal());
        } else {
            telegramUser = optionalTelegramUser.get();
            telegramUser.setActive(true);
            userState = userStateService.findByChatId(chatId).get();
        }
        telegramUserService.save(telegramUser);
        userStateService.save(userState);
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
