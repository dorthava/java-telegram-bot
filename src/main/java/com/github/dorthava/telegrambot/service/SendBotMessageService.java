package com.github.dorthava.telegrambot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}