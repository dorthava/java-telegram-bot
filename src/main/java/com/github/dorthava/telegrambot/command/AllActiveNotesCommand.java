package com.github.dorthava.telegrambot.command;

import com.github.dorthava.telegrambot.models.Note;
import com.github.dorthava.telegrambot.service.NoteService;
import com.github.dorthava.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllActiveNotesCommand implements Command {

    public static final String ALL_ACTIVE_NOTES_MESSAGE = "✨Все активные заметки:✨\n";
    private final SendBotMessageService sendBotMessageService;
    private final NoteService noteService;

    public AllActiveNotesCommand(SendBotMessageService sendBotMessageService, NoteService noteService) {
        this.sendBotMessageService = sendBotMessageService;
        this.noteService = noteService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Note> allActiveNotes = noteService.retrieveAllActiveNotes(chatId);

        sendBotMessageService.sendMessage(chatId, ALL_ACTIVE_NOTES_MESSAGE);
        StringBuilder sb = new StringBuilder();
        sb.append("╔═════════════════════════╗\n");
        int id = 1;
        for(Note note : allActiveNotes) {
            sb.append(String.format("ID: %d\nContent: %s\nTime: %s", id, note.getText(),
                    note.getNotificationTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
            sb.append("\n╠═════════════════════════╣\n");
            ++id;
        }
        sb.append("╚═════════════════════════╝\n");
        sendBotMessageService.sendMessage(chatId, sb.toString());
    }
}
