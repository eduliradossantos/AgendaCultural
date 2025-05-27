package controller;

import dto.CommentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST - criar comentário
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO created = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET - listar comentários de um evento
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByEvent(@PathVariable UUID eventId) {
        List<CommentDTO> comments = commentService.getCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }

    // DELETE - excluir comentário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
