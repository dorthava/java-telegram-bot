package com.github.dorthava.telegrambot.repository;

import com.github.dorthava.telegrambot.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByChatIdAndActiveTrue(String chatId);
    List<Note> findAllByNotificationTimeLessThanEqual(LocalDateTime notificationTime);
}
