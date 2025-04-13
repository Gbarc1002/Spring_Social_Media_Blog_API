package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

    @Query("FROM Account WHERE username = :username")
    public Optional<Account> findAccountByUsername(@Param("username") String username);

    @Query("FROM Account WHERE username = :username AND password = :password")
    public Optional<Account> findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("FROM Account WHERE accountId = :accountId")
    public Optional<Account> findAccountByAccountId(@Param("accountId") int accountId);
}
