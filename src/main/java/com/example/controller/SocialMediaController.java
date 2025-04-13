package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> postAccount(@RequestBody Account accountInput) {
        if (    accountInput.getUsername().isBlank() || 
                accountInput.getPassword().isBlank() ||
                accountInput.getPassword().length() < 4) {
            
            return ResponseEntity.status(400).body(null);

        } else if (accountService.accountExistsByUsername(accountInput.getUsername())) {
            return ResponseEntity.status(409).body(null);
        } else {
            Account account = accountService.addAccount(accountInput);
            return ResponseEntity.status(200).body(account);
        }
        
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account input) {
        Account account = accountService.loginCredentials(input);
        if (account != null) {
            return ResponseEntity.status(200).body(account);
        }
        return ResponseEntity.status(401).body(null);
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> messages(@RequestBody Message message) {
        if (    !accountService.accountExistsById(message.getPostedBy()) ||
                message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return ResponseEntity.status(400).body(null);
        } else return ResponseEntity.status(200).body(messageService.addMessage(message));
    }

    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> allMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        if (messageService.messageExists(messageId)) {
            return ResponseEntity.status(200).body(messageService.deleteMessage(messageId));
        }
        return ResponseEntity.status(200).body(null);
    }
}
