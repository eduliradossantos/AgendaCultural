package mapper;

import dto.CommentDTO;
import model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment toEntity(CommentDTO dto) {
        return Comment.builder()
                .idComment(dto.getIdComment())
                .text(dto.getText())
                .date(dto.getDate()) // será preenchido automaticamente se nulo
                .build();
    }

    public CommentDTO toDTO(Comment entity) {
        CommentDTO dto = new CommentDTO();
        dto.setIdComment(entity.getIdComment());
        dto.setText(entity.getText());
        dto.setDate(entity.getDate());
        dto.setUserId(entity.getUser().getIdUser());
        dto.setEventId(entity.getEvent().getIdEvent());
        return dto;
    }

    public List<CommentDTO> toDTOList(List<Comment> comments) {
        return comments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
