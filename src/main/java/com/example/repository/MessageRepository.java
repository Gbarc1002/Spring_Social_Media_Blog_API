package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("FROM Message WHERE messageId = :messageId")
    public Message findMessageByMessageId(@Param("messageId") int messageId);

    @Query("FROM Message WHERE messageId = :messageId")
    public Optional<Message> findMessageByMessageIdOptional(@Param("messageId") int messageId);

    @Query("FROM Message WHERE postedBy = :postedBy")
    public List<Message> findMessagesByPostedBy(@Param("postedBy") int postedBy);
}
