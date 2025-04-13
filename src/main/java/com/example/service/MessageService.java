package com.example.service;

import java.util.List;
import java.util.Optional;

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

    public List<Message> getAllMessages() {
        List<Message> allMessages = messageRepository.findAll();
        return allMessages;
    }

    public Message getMessageById(int messageId) {
        Message message = messageRepository.findMessageByMessageId(messageId);
        return message;
    }

    public Boolean messageExists(int messageId) {
        Optional<Message> messageOptional = messageRepository.findMessageByMessageIdOptional(messageId);
        if (messageOptional.isPresent()) {
            return true;
        } else return false;
    }

    public int deleteMessage(int messageId) {
        int rows = 0;
        Optional<Message> messageOptional = messageRepository.findMessageByMessageIdOptional(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageRepository.findMessageByMessageId(messageId);
            messageRepository.delete(message);
            rows++;
        }
        return rows;
    }

    public int updateMessage(int messageId, String messageText) {
        Optional<Message> messageOptional = messageRepository.findMessageByMessageIdOptional(messageId);
        int rows = 0;
        if (messageOptional.isPresent()) {
            Message oldMessage = messageOptional.get();
            Message newMessage = new Message(   oldMessage.getMessageId(), 
                                                oldMessage.getPostedBy(), 
                                                messageText, 
                                                oldMessage.getTimePostedEpoch());
            messageRepository.save(newMessage);
            rows++;
        }
        return rows;
    }

    public List<Message> getAllMessagesFromUser(int accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }
}
