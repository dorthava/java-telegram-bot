package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventCheckServiceImpl implements EventCheckService {
    private final SendBotMessageService sendBotMessageService;
    private final NoteService noteService;

    @Autowired
    public EventCheckServiceImpl(NoteService noteService, SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        this.noteService = noteService;
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void checkEvents() {
        List<Note> eventsThatHappened = checkForEvents();
        if (!eventsThatHappened.isEmpty()) notifyUsers(eventsThatHappened);
    }

    @Override
    public List<Note> checkForEvents() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        List<Note> noteList = noteService.findAllByNotificationTimeLessThanEqual(nowLocalDateTime);
        return noteList;
    }

    @Override
    public void notifyUsers(List<Note> noteList) {
        for (Note note : noteList) {
            String chatId = note.getChatId();
            sendBotMessageService.sendMessage(chatId, note.getText());
            noteService.deleteNoteById(note.getId());
        }
    }
}
