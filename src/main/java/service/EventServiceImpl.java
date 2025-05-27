package service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);


    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
public EventDTO createEvent(EventDTO eventDTO) {
    logger.info("Creating a new event with title: {}", eventDTO.getTitle());
    Event event = eventMapper.toEntity(eventDTO);
    Event savedEvent = eventRepository.save(event);
    logger.info("Event created with ID: {}", savedEvent.getIdEvent());
    return eventMapper.toDTO(savedEvent);
}

@Override
public List<EventDTO> searchEventsByName(String name) {
    logger.info("Searching events with name containing: {}", name);
    List<Event> events = eventRepository.findByNameContainingIgnoreCase(name);
    logger.info("Found {} events", events.size());
    return eventMapper.toDTOList(events);
}

@Override
public EventDTO updateEvent(UUID id, EventDTO eventDTO) {
    logger.info("Updating event with ID: {}", id);
    Event event = eventRepository.findById(id)
        .orElseThrow(() -> {
            logger.error("Event not found with ID: {}", id);
            return new ResourceNotFoundException("Event not found with id " + id);
        });

    event.setTitle(eventDTO.getTitle());
    event.setDateTime(eventDTO.getDateTime());
    event.setLocation(eventDTO.getLocation());

    Event updatedEvent = eventRepository.save(event);
    logger.info("Event updated: {}", updatedEvent.getIdEvent());
    return eventMapper.toDTO(updatedEvent);
}

@Override
public void deleteEvent(UUID id) {
    logger.info("Deleting event with ID: {}", id);
    if (!eventRepository.existsById(id)) {
        logger.error("Event not found with ID: {}", id);
        throw new ResourceNotFoundException("Event not found with id " + id);
    }
    eventRepository.deleteById(id);
    logger.info("Event deleted: {}", id);
}

@Override
public EventDTO getEventById(UUID id) {
    logger.info("Getting event by ID: {}", id);
    Event event = eventRepository.findById(id)
        .orElseThrow(() -> {
            logger.error("Event not found with ID: {}", id);
            return new ResourceNotFoundException("Event not found with id: " + id);
        });
    return eventMapper.toDTO(event);
}

@Override
public List<EventDTO> getAllEvents() {
    logger.info("Fetching all events");
    List<Event> events = eventRepository.findAll();
    logger.info("Total events found: {}", events.size());
    return eventMapper.toDTOList(events);
}


}
