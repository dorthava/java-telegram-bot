package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.Note;
import com.github.dorthava.telegrambot.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> retrieveAllActiveNotes(String chatId);
    List<Note> retrieveAllNotActiveNotes(String chatId);
    void save(Note note);
    Optional<Note> findById(Long id);
    void deleteNoteById(Long id);
}
