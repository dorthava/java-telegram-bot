package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContainer {
    public static final String COMMAND_PREFIX = "/";
    private final Command noCommand;
    private final Command unknownCommand;
    private final Map<String, Command> commandMap;


    @Autowired
    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.noCommand = new NoCommand(sendBotMessageService);
        this.unknownCommand = new UnknownCommand(sendBotMessageService);

        this.commandMap = new HashMap<>();
        this.commandMap.put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService));
        this.commandMap.put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        this.commandMap.put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService));
        this.commandMap.put(CommandName.STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService));
    }

    public Command retrieveCommand(String commandIdentifier) {
        Command resultCommand;
        if(commandIdentifier.startsWith(COMMAND_PREFIX)) {
            resultCommand = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        } else {
            resultCommand = noCommand;
        }
        return resultCommand;
    }
}
