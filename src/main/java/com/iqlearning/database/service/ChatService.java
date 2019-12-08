package com.iqlearning.database.service;

import com.iqlearning.database.entities.Chat;
import com.iqlearning.database.repository.ChatRepository;
import com.iqlearning.database.service.interfaces.IChatService;
import com.iqlearning.database.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService implements IChatService{

    @Autowired
    ChatRepository repo;

    @Override
    public List<Message> getConversationHistory(Long user1, Long user2) {
        List<Message> history = new ArrayList<>();
        List<Chat> chatHistory = repo.getAllByUser1AndUser2OrderBySentOnDesc(user1,user2);
        if(chatHistory == null) chatHistory = repo.getAllByUser1AndUser2OrderBySentOnDesc(user2,user1);
        Message m;
        for (Chat c:chatHistory) {
            m = new Message(c.getMessage());
            m.setSender(c.getSender());
            if(c.getUser1().equals(c.getSender())) m.setRecipient(c.getUser2());
            else m.setRecipient(c.getUser1());
            history.add(m);
        }
        return history;
    }

    @Override
    public Message sendMessage(Message m) {
        Chat c = new Chat();
        c.setMessage(m.getMessage());
        if(repo.countAllByUser1AndUser2(m.getRecipient(),m.getSender()) > 0) {
            c.setUser2(m.getSender());
            c.setUser1(m.getRecipient());
        }else {
            c.setUser1(m.getSender());
            c.setUser2(m.getRecipient());
        }
        c.setSender(m.getSender());
        c.setSentOn(new Timestamp(System.currentTimeMillis()));
        c = repo.save(c);
        Message message = new Message(c.getMessage());
        message.setSender(c.getSender());
        message.setSent(new Timestamp(System.currentTimeMillis()));
        if(c.getUser1().equals(c.getSender())) message.setRecipient(c.getUser2());
        else message.setRecipient(c.getUser1());
        return message;
    }
}
