package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.utils.Message;

import java.util.List;

public interface IChatService {

    List<Message> getConversationHistory(Long convoId);
    Message sendMessage(Message m, Long convoId);
    void readMessage(Long recipient, Long sender);
    List<Message> getUnread(Long user);
}
