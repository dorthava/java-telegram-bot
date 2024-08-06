package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.Note;

import java.util.List;

public interface EventCheckService {
    void checkEvents();
    List<Note> checkForEvents();
    void notifyUsers(List<Note> noteList);
}
