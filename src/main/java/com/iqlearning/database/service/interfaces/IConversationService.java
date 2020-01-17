package com.iqlearning.database.service.interfaces;


import java.util.HashMap;


public interface IConversationService {

    HashMap<Long,String> getAllUserConversation(Long userId);
}

