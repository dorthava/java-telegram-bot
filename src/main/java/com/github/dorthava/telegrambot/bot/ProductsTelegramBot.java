package com.github.dorthava.telegrambot.bot;

import com.github.dorthava.telegrambot.command.CommandContainer;
import com.github.dorthava.telegrambot.command.CommandName;
import com.github.dorthava.telegrambot.models.UserState;
import com.github.dorthava.telegrambot.service.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class ProductsTelegramBot extends TelegramLongPollingBot {
    private final CommandContainer commandContainer;
    private final UserStateService userStateService;

    @Value("${bot.username}")
    String username;

    @Value("${bot.token}")
    String token;

    @Autowired
    public ProductsTelegramBot(@Lazy CommandContainer commandContainer, UserStateService userStateService) {
        this.userStateService = userStateService;
        this.commandContainer = commandContainer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && update.getMessage().hasText()) {
            Optional<UserState> optionalUserState = userStateService.findByChatId(chatId);
            if(optionalUserState.isEmpty() || optionalUserState.get().getState() == 0) {
                String message = update.getMessage().getText().trim();
                commandContainer.retrieveCommand(message).execute(update);
            } else {
                commandContainer.retrieveCommand(CommandName.CREATE_NOTE.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
