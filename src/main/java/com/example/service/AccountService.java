package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        Account newAccount = new Account(account.getUsername(), account.getPassword());
        return accountRepository.save(newAccount);
    }

    public Boolean accountExistsByUsername(String username) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsername(username);
        if (accountOptional.isPresent()) {
            return true;
        }
        return false;
    }

    public Account loginCredentials(Account account) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        }
        return null;
    }
}
