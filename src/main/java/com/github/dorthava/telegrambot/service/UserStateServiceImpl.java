package com.github.dorthava.telegrambot.service;

import com.github.dorthava.telegrambot.models.State;
import com.github.dorthava.telegrambot.models.UserState;
import com.github.dorthava.telegrambot.repository.UserStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStateServiceImpl implements UserStateService {
    private final UserStateRepository userStateRepository;

    @Autowired
    public UserStateServiceImpl(UserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }

    @Override
    public void save(UserState userState) {
        userStateRepository.save(userState);
    }

    @Override
    public Optional<UserState> findByChatId(String chatId) {
        return userStateRepository.findByChatId(chatId);
    }

    @Override
    public void updateState(String chatId, Integer state) {
        userStateRepository.updateState(chatId, state);
    }

    @Override
    public void updateStateNote(String chatId, Long noteId) {
        userStateRepository.updateStateNote(chatId, noteId);
    }
}
