package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.service.SendBotMessageService;
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
    public CommandContainer(SendBotMessageService sendBotMessageService) {
        this.noCommand = new NoCommand(sendBotMessageService);
        this.unknownCommand = new UnknownCommand(sendBotMessageService);

        this.commandMap = new HashMap<>();
        this.commandMap.put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService));
        this.commandMap.put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        this.commandMap.put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService));
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
