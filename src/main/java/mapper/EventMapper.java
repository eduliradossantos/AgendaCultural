package mapper;

import dto.EventDTO;
import model.Category;
import model.Event;
import model.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

	public Event toEntity(EventDTO dto) {
        return Event.builder()
                .idEvent(dto.getIdEvent())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .dateTime(dto.getDateTime())
                .category(dto.getCategoryId() != null ? new Category(dto.getCategoryId()) : null)
                .createdBy(dto.getCreatedById() != null ? createUserFromId(dto.getCreatedById()) : null)
                .build();
    }

    public EventDTO toDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setIdEvent(event.getIdEvent());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDateTime(event.getDateTime());
        dto.setCategoryId(event.getCategory() != null ? event.getCategory().getIdCategory() : null);
        dto.setCreatedById(event.getCreatedBy() != null ? event.getCreatedBy().getIdUser() : null);

        return dto;
    }

    public List<EventDTO> toDTOList(List<Event> events) {
        return events.stream()
                     .map(this::toDTO)
                     .collect(Collectors.toList());
    }
    
    private User createUserFromId(UUID id) {
        User user = new User();
        user.setIdUser(id);
        return user;
    }
    
}
