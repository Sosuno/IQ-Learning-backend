package com.iqlearning.chat;

import com.iqlearning.database.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value="/api/chat/{userId}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class )
public class ChatEndpoint {

    @Autowired
    private ChatService chatService;

    private Session session;
    private static Set<ChatEndpoint> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, Long> users = new HashMap<>();

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("userId") Long userId) throws IOException, EncodeException {

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), userId);

        Message message = new Message();
        message.setFrom(userId);
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, EncodeException {

        message.setFrom(users.get(session.getId()));
        com.iqlearning.database.utils.Message m = new com.iqlearning.database.utils.Message(message.getContent(),message.getFrom(),message.getTo());
        m = chatService.sendMessage(m);
        message = new Message(m.getSender(),m.getRecipient(),m.getMessage(),m.getSent());
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}