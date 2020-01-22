package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation,Long> {

    @Query("SELECT c FROM Conversation c WHERE user1 = :user OR user2 = :user ORDER BY lastMessage")
    List<Conversation> getUserConversations(@Param("user")Long user);

    @Query("SELECT c FROM Conversation c WHERE (user1 = :user1 OR user2 = :user1) AND (user1 = :user2 OR user2 = :user2)")
    Conversation checkIfConvoExists(@Param("user1")Long user1, @Param("user2")Long user2);

}
