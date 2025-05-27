package service;

import dto.CommentDTO;
import exception.ResourceNotFoundException;
import mapper.CommentMapper;
import model.Comment;
import model.Event;
import model.User;
import org.springframework.stereotype.Service;
import repository.CommentRepository;
import repository.EventRepository;
import repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper,
                              UserRepository userRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Event event = eventRepository.findById(commentDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setUser(user);
        comment.setEvent(event);

        Comment saved = commentRepository.save(comment);
        return commentMapper.toDTO(saved);
    }

    @Override
    public List<CommentDTO> getCommentsByEventId(UUID eventId) {
        List<Comment> comments = commentRepository.findByEventIdOrderByDateDesc(eventId);
        return commentMapper.toDTOList(comments);
    }

    @Override
    public void deleteComment(UUID id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comentário não encontrado");
        }
        commentRepository.deleteById(id);
    }
}
