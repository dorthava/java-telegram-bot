package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.State;
import com.github.dorthava.telegrambot.models.UserState;

import java.util.Optional;

public interface UserStateService {
    void save(UserState userState);
    Optional<UserState> findByChatId(String chatId);
    void updateState(String chatId, Integer state);
    void updateStateNote(String chatId, Long noteId);
}
