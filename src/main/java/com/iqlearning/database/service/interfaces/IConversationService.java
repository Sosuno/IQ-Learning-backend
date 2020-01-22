package com.iqlearning.database.service.interfaces;


import com.iqlearning.database.utils.ChatUser;

import java.util.HashMap;


public interface IConversationService {

    HashMap<Long,ChatUser> getAllUserConversation(Long userId);
}

