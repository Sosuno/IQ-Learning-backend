package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Chat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat,Long> {

    //List<Chat> getAllByUser1AndUser2OrderBySentOnDesc(Long user1, Long user2);

    List<Chat> getAllByConversationOrderBySentOn(Long convoId);

    @Query("SELECT c FROM Chat c WHERE NOT(sender = :user) AND (user1 = :user OR user2 = :user) AND read = false")
    List<Chat> getAllUnread(@Param("user") Long user);

    @Modifying
    @Query("UPDATE Chat SET read = true WHERE sender = :sender AND (user1 = :recipient OR user2 = :recipient)")
    void readMessages(@Param("sender") Long sender,@Param("recipient") Long recipient);
}
