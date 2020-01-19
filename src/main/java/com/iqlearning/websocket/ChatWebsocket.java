package com.iqlearning.websocket;



import com.iqlearning.database.service.ChatService;
import com.iqlearning.database.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
@CrossOrigin(origins=("http://localhost:3000"))
public class ChatWebsocket {

    @Autowired
    private ChatService chatService;

    @CrossOrigin(origins=("http://localhost:3000"))
    @MessageMapping("/sendMessage/{conversationId}")
    @SendTo("/topic/{conversationId}")
    public ResponseEntity<?> sendMessage(@DestinationVariable("conversationId") Long conversationId, MessageForm message) throws Exception {

        Message m = new Message(message.getMessage(),message.getSender(), message.getRecipient());
        m.setMessage(message.getMessage());
        m.setRecipient(message.getRecipient());
        System.out.println(message.getRecipient());
        Message mes = chatService.sendMessage(m,conversationId);

        System.out.println(mes.toString());

        return new ResponseEntity<Object>(mes, HttpStatus.OK);
    }
    @CrossOrigin(origins=("http://localhost:3000"))
    @MessageMapping("/startConversation/{userId}")
    @SendTo("/user/{userId}")
    public ResponseEntity<?> startConversation(@DestinationVariable("userId") Long conversationId, MessageForm message) throws Exception {
        Message m = new Message(message.getMessage(),message.getSender(), message.getRecipient());
        m.setMessage(message.getMessage());
        m.setRecipient(message.getRecipient());
        Message mes = chatService.sendMessage(m,conversationId);

        return new ResponseEntity<Object>(mes, HttpStatus.OK);

    }
}