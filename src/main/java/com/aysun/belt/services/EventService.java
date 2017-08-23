package com.aysun.belt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aysun.belt.models.Event;
import com.aysun.belt.repositories.EventRepo;


@Service
public class EventService {
	
	private EventRepo eventRepo;
	
	public EventService(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}
	
	public void createEvent(Event event) {
		eventRepo.save(event);
	}
	
	public List<Event> findEventsByState(String state) {
		return eventRepo.findByState(state);
	}
	
	public List<Event> findEventsNotByState(String state) {
		return eventRepo.findEventsNotByState(state);
	}
	
	public Event findEventById(Long id) {
		return eventRepo.findOne(id);
	}
	
	public void deleteEvent(Long id) {
		eventRepo.delete(id);
	}

}