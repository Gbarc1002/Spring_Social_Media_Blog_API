package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
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

    
}
