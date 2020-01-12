package com.iqlearning.rest;

import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.IChatService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.database.utils.Message;
import com.iqlearning.rest.resource.MessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class ChatController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IChatService chatService;

    @PutMapping("/chat/send")
    public ResponseEntity<?> sendMessage(@RequestHeader Map<String, String> headers, @RequestBody MessageForm messageForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(userService.getUser(messageForm.getRecipient()) == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        if(messageForm.getMessage() == null) return new ResponseEntity<>("Message is empty", HttpStatus.BAD_REQUEST);
        Message message = new Message(messageForm.getMessage(), user.getId(), messageForm.getRecipient());
        Message sentMessage = chatService.sendMessage(message);
        return new ResponseEntity<>(sentMessage, HttpStatus.OK);
    }

    @GetMapping("/chat/get/{id}")
    public ResponseEntity<?> getMessages(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if (userService.getUser(id) == null) return new ResponseEntity<>("User with id" + id + "doesn't exist", HttpStatus.BAD_REQUEST);
        List<Message> chatHistory = chatService.getConversationHistory(user.getId(), id);
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }
}