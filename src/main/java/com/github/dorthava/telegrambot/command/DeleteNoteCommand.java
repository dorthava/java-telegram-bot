package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.Note;
import com.github.dorthava.telegrambot.models.State;
import com.github.dorthava.telegrambot.models.UserState;
import com.github.dorthava.telegrambot.service.NoteService;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.UserStateService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

public class DeleteNoteCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final NoteService noteService;
    private final UserStateService userStateService;

    public DeleteNoteCommand(SendBotMessageService sendBotMessageService, NoteService noteService, UserStateService userStateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.noteService = noteService;
        this.userStateService = userStateService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Optional<UserState> optionalUserState = userStateService.findByChatId(chatId);
        if(optionalUserState.isEmpty()) {
            sendBotMessageService.sendMessage(chatId, "При попытке удалить заметку произошла ошибка.\n");
            return;
        }

        UserState userState = optionalUserState.get();
        if(userState.getState() == State.WAITING_FOR_COMMAND.ordinal()) {
            sendBotMessageService.sendMessage(chatId, "Введите ID удаляемой заметки:\n");
            AllActiveNotesCommand allActiveNotesCommand = new AllActiveNotesCommand(sendBotMessageService, noteService);
            allActiveNotesCommand.execute(update);
            userState.setState(State.WAITING_FOR_INDEX_TO_BE_DELETED.ordinal());
        } else if(userState.getState() == State.WAITING_FOR_INDEX_TO_BE_DELETED.ordinal()) {
            List<Note> noteList = noteService.retrieveAllActiveNotes(chatId);
            try {
                int index = Integer.parseInt(update.getMessage().getText().toString());
                Note note = noteList.get(index - 1);
                noteService.deleteNoteById(note.getId());
                userState.setState(State.WAITING_FOR_COMMAND.ordinal());
                sendBotMessageService.sendMessage(chatId, "Удаление прошло успешно!:)");
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                sendBotMessageService.sendMessage(chatId, "Некорректный индекс:(\nПопробуйте снова.");
                userState.setState(State.WAITING_FOR_COMMAND.ordinal());
                System.err.println(e.getMessage());
            }
        }
        userStateService.save(userState);
    }
}
