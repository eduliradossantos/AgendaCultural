package controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import dto.EventDTO;
import jakarta.validation.Valid;
import service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        logger.info("POST /api/events - Creating event");
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<EventDTO>> searchByName(@RequestParam String name) {
        logger.info("GET /api/events/search?name={} - Searching events", name);
        List<EventDTO> events = eventService.searchEventsByName(name);
        return ResponseEntity.ok(events);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable UUID id, @Valid @RequestBody EventDTO eventDTO) {
        logger.info("PUT /api/events/{} - Updating event", id);
        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        logger.info("DELETE /api/events/{} - Deleting event", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    
}
