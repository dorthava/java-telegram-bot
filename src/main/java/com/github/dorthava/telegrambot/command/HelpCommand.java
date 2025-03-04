package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n"
                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "<b>Работа с заметками:</b>\n"
                    + "%s - создание активной заметки\n"
                    + "%s - удаление заметки\n"
                    + "%s - получить весь список активных заметок\n",
            CommandName.START.getCommandName(), CommandName.STOP.getCommandName(),
            CommandName.HELP.getCommandName(), CommandName.CREATE_NOTE.getCommandName(),
            CommandName.DELETE_NOTE.getCommandName(), CommandName.ALL_ACTIVE_NOTES.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}