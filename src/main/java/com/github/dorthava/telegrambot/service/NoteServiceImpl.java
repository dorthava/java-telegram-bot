package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.Note;
import com.github.dorthava.telegrambot.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> retrieveAllActiveNotes(String chatId) {
        return noteRepository.findAllByChatIdAndActiveTrue(chatId);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> findAllByNotificationTimeLessThanEqual(LocalDateTime notificationTime) {
        return noteRepository.findAllByNotificationTimeLessThanEqual(notificationTime);
    }
}
