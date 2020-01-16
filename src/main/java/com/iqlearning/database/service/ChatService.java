package com.iqlearning.database.service;

import com.iqlearning.database.entities.Chat;
import com.iqlearning.database.repository.ChatRepository;
import com.iqlearning.database.service.interfaces.IChatService;
import com.iqlearning.database.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService{

    @Autowired
    ChatRepository repo;

    @Override
    public List<Message> getConversationHistory(Long user1, Long user2) {
        List<Message> history = new ArrayList<>();
        List<Chat> chatHistory = repo.getAllByUser1AndUser2OrderBySentOnDesc(user1,user2);
        if(chatHistory.isEmpty()) chatHistory = repo.getAllByUser1AndUser2OrderBySentOnDesc(user2,user1);
        Message m;
        for (Chat c:chatHistory) {
            m = new Message(c);
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
        return new Message(c);
    }

    @Override
    public List<Message> getUnread(Long user) {
        List<Message> history = new ArrayList<>();
        List<Chat> chat = repo.getAllUnread(user);
        if(!chat.isEmpty()) {
            Message m;
            for (Chat c : chat) {
                m = new Message(c);
                history.add(m);
            }
        }
        return history;
    }

    @Transactional
    @Override
    public void readMessage(Long recipient, Long sender) {
      repo.readMessages(sender,recipient);
    }
}
