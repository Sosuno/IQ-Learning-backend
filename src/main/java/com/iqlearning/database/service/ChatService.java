package com.iqlearning.database.service;

import com.iqlearning.database.entities.Chat;
import com.iqlearning.database.entities.Conversation;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.repository.ChatRepository;
import com.iqlearning.database.repository.ConversationRepository;
import com.iqlearning.database.repository.UserRepository;
import com.iqlearning.database.service.interfaces.IChatService;
import com.iqlearning.database.service.interfaces.IConversationService;
import com.iqlearning.database.utils.ChatUser;
import com.iqlearning.database.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService, IConversationService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Message> getConversationHistory(Long convoId) {
        if(getConversation(convoId) != null) {
            List<Message> history = new ArrayList<>();
            List<Chat> chatHistory = chatRepository.getAllByConversationOrderBySentOn(convoId);
            Message m;
            for (Chat c : chatHistory) {
                m = new Message(c);
                history.add(m);
            }
            return history;
        }
        return null;
    }

    @Override
    public Message sendMessage(Message m, Long convoId) {
        Conversation conversation;
        if(convoId == null) {
            System.out.println("covoId: " + convoId);
            conversation = conversationRepository.checkIfConvoExists(m.getSender(),m.getRecipient());
            System.out.println("almost in if");
            System.out.println("show id: " + conversation.getId());
            if(conversation == null) {
                System.out.println("In if");
                conversation = new Conversation(m.getSender(), m.getRecipient());
                conversation = conversationRepository.save(conversation);
            }
        }else conversation = getConversation(convoId);
        Chat c = new Chat();
        c.setMessage(m.getMessage());
        c.setConversation(conversation.getId());
        c.setSender(m.getSender());
        c.setSentOn(new Timestamp(System.currentTimeMillis()));
        c = chatRepository.save(c);
        Optional<User> o;
        String recipientName = "";
        o = userRepository.findById(m.getRecipient());
        if(o.isPresent()) recipientName = o.get().getUsername();

        return new Message(c,m.getRecipient(),recipientName);
    }

    @Override
    public List<Message> getUnread(Long user) {
        List<Message> history = new ArrayList<>();
        List<Chat> chat = chatRepository.getAllUnread(user);
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
      chatRepository.readMessages(sender,recipient);
    }

    @Override
    public HashMap<Long,ChatUser> getAllUserConversation(Long userId) {
        List<Conversation> convos = conversationRepository.getUserConversations(userId);
        Optional<User> o;
        HashMap<Long,ChatUser> convosMap = new HashMap<>();

        for(Conversation l: convos) {
            if(userId.equals(l.getUser1())) o = userRepository.findById(l.getUser2());
            else o = userRepository.findById(l.getUser1());
                if(o.isPresent()) {
                    convosMap.put(l.getId(), new ChatUser(o.get().getId(),o.get().getUsername(),o.get().getAvatar()));
                }
        }
        return convosMap;
    }


    public Conversation getConversation(Long convoId){
        Optional<Conversation> o = conversationRepository.findById(convoId);
        if (o.isPresent()) return o.get();
        else return null;
    }
}
