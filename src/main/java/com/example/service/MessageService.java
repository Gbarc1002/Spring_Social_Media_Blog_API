package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message) {
        Message postedMessage = new Message(    message.getPostedBy(), 
                                                message.getMessageText(), 
                                                message.getTimePostedEpoch());
        messageRepository.save(postedMessage);
        return postedMessage;
    }
}
