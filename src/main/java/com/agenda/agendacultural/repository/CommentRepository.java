package com.agenda.agendacultural.repository;

import com.agenda.agendacultural.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByEvent_IdEventOrderByDateDesc(UUID eventId);
}
