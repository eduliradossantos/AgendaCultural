package service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dto.EventDTO;
import repository.EventRepository;
import mapper.EventMapper;
import model.Event;
import exception.ResourceNotFoundException;


@Service
public class EventServiceImpl implements EventService{

	private final EventRepository eventRepository;
    private final EventMapper eventMapper; // Para converter entre entidade e DTO

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        // Converter DTO para entidade
        Event event = eventMapper.toEntity(eventDTO);
        // Salvar no banco
        Event savedEvent = eventRepository.save(event);
        // Converter entidade salva para DTO e retornar
        return eventMapper.toDTO(savedEvent);
    }

    @Override
    public List<EventDTO> searchEventsByName(String name) {
        // Consultar banco pelo nome usando método no repository (exemplo)
        List<Event> events = eventRepository.findByNameContainingIgnoreCase(name);
        // Converter lista de entidade para lista de DTOs
        return eventMapper.toDTOList(events);
    }

    @Override
    public EventDTO updateEvent(UUID id, EventDTO eventDTO) {
        // Buscar o evento no banco, se não existir, lançar exceção (exemplo)
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        // Atualizar os campos do evento com os dados do DTO
        event.setTitle(eventDTO.getTitle());
        event.setDateTime(eventDTO.getDateTime());
        event.setLocation(eventDTO.getLocation());

        // Salvar atualização no banco
        Event updatedEvent = eventRepository.save(event);
        // Retornar DTO atualizado
        return eventMapper.toDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(UUID id) {
        // Verificar se o evento existe
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        // Deletar evento
        eventRepository.deleteById(id);
    }

    @Override
public EventDTO getEventById(UUID id) {
    Event event = eventRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    return eventMapper.toDTO(event);
}

@Override
public List<EventDTO> getAllEvents() {
    List<Event> events = eventRepository.findAll();
    return eventMapper.toDTOList(events);
}

}
