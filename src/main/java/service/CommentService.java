package service;

import dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);
    List<CommentDTO> getCommentsByEventId(UUID eventId);
    void deleteComment(UUID id);
}
