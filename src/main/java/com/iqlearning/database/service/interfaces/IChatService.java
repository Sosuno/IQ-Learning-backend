package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.utils.Message;

import java.util.List;

public interface IChatService {

    List<Message> getConversationHistory(Long user1, Long user2);
    Message sendMessage(Message m);
    void readMessage(Long recipient, Long sender);
    List<Message> getUnread(Long user);
}
