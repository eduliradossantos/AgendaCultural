package repository;

import java.util.List;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Event;

public interface EventRepository extends JpaRepository<Event, UUID>{
	List<Event> findByNameContainingIgnoreCase(String name);
}
