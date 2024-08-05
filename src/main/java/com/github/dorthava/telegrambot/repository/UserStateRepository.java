package com.github.dorthava.telegrambot.repository;

import com.github.dorthava.telegrambot.models.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {
    Optional<UserState> findByChatId(String chatId);
    @Modifying
    @Transactional
    @Query("UPDATE UserState us SET us.state = :state WHERE us.chatId = :chatId")
    void updateState(@Param("chatId") String chatId, @Param("state") Integer state);

    @Modifying
    @Transactional
    @Query("UPDATE UserState u SET u.noteId = :noteId WHERE u.chatId = :chatId")
    void updateStateNote(@Param("chatId") String chatId, @Param("noteId") Long noteId);
}
