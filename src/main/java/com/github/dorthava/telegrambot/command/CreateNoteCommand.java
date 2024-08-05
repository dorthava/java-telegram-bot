package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.Note;
import com.github.dorthava.telegrambot.models.State;
import com.github.dorthava.telegrambot.models.UserState;
import com.github.dorthava.telegrambot.service.NoteService;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import com.github.dorthava.telegrambot.service.UserStateService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class CreateNoteCommand implements Command {
    public final SendBotMessageService sendBotMessageService;
    public final NoteService noteService;
    private final UserStateService userStateService;

    public CreateNoteCommand(SendBotMessageService sendBotMessageService, NoteService noteService, UserStateService userStateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.noteService = noteService;
        this.userStateService = userStateService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Optional<UserState> optionalUserState = userStateService.findByChatId(chatId);

        if(optionalUserState.isPresent()) {
            UserState userState = optionalUserState.get();
            if(userState.getState() == State.WAITING_FOR_COMMAND.ordinal()) {
                sendBotMessageService.sendMessage(chatId, "Введите ваше сообщение:\n");
                userStateService.updateState(chatId, State.WAITING_FOR_CONTENT.ordinal());
            } else if(userState.getState() == State.WAITING_FOR_CONTENT.ordinal()) {
                Note note = new Note();
                note.setChatId(chatId);
                note.setText(update.getMessage().getText());
                note.setActive(true);
                noteService.save(note);
                userStateService.updateStateNote(chatId, note.getId());
                userStateService.updateState(chatId, State.WAITING_FOR_NOTIFICATION_TIME.ordinal());
                sendBotMessageService.sendMessage(chatId, "Введите дату уведомления в формате \"yyyy-MM-dd HH:mm:ss\":\n");
            } else {
                userStateService.updateState(chatId, State.WAITING_FOR_COMMAND.ordinal());
                String stringLocalDateTime = update.getMessage().getText();
                Optional<Note> optionalNote = noteService.findById(userState.getNoteId());
                Note note = optionalNote.get();
                try {
                    LocalDateTime localDateTime = LocalDateTime.parse(stringLocalDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    note.setNotificationTime(localDateTime);
                    noteService.save(note);
                    sendBotMessageService.sendMessage(chatId, "Заметка успешно создана!\n");
                } catch (DateTimeParseException e) {
                    System.err.println(e.getMessage());
                    noteService.deleteNoteById(userState.getNoteId());
                }
            }
        }
    }
}
