package com.aysun.belt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aysun.belt.models.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {
	List<Event> findByState(String state);
	@Query(value = "SELECT * FROM events WHERE state not in (?1)", nativeQuery = true)
	List<Event> findEventsNotByState(String state);
}

