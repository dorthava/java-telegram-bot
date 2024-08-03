package com.github.dorthava.telegrambot.bot;

import com.github.dorthava.telegrambot.command.CommandContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ProductsTelegramBot extends TelegramLongPollingBot {
    private final CommandContainer commandContainer;

    @Value("${bot.username}")
    String username;

    @Value("${bot.token}")
    String token;

    @Autowired
    public ProductsTelegramBot(@Lazy CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            commandContainer.retrieveCommand(message).execute(update);
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
