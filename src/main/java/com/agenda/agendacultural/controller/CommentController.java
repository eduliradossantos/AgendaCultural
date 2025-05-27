package com.agenda.agendacultural.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agenda.agendacultural.dto.CommentDTO;
import jakarta.validation.Valid;
import com.agenda.agendacultural.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        logger.info("POST /api/comments - Creating comment");
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByEventId(@PathVariable UUID eventId) {
        logger.info("GET /api/comments/event/{} - Getting comments for event", eventId);
        List<CommentDTO> comments = commentService.getCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        logger.info("DELETE /api/comments/{} - Deleting comment", id);
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
