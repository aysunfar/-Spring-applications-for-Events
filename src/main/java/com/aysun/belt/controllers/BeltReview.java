package com.aysun.belt.controllers;

import java.security.Principal;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aysun.belt.models.Event;
import com.aysun.belt.models.Post;
import com.aysun.belt.models.User;
import com.aysun.belt.services.EventService;
import com.aysun.belt.services.UserService;

import com.aysun.belt.services.PostService;

@Controller

public class BeltReview {
	String[] states = {"California", "Alabama", "Arkansas", "Arizona", "Alaska", "Colorado", "Connecticut", 
			"Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", 
			"Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", 
			"Montana", "Nebraska,", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", 
			"North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", 
			"Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
	private UserService userService;
	private EventService eventService;
	private PostService postService;
	
	@Autowired
	public BeltReview(UserService userService, EventService eventService, PostService postService) {
		this.userService = userService;
		this.eventService = eventService;
		this.postService = postService;
	
	}
	
	@GetMapping("/")
	public String login(
			@Valid @ModelAttribute("user") User user, 
			Model model, @RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Principal principal
			) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid credentials!");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "You logged out successfully!");
		}
		model.addAttribute("states", states);
		return "login.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		model.addAttribute("states", states);
		if (result.hasErrors()) {
			return "login.jsp";
		} else {
			userService.createUser(user);
			return "redirect:/";
		}
	}
	
	@GetMapping("/events")
	public String allEvents(Principal principal, Model model, @Valid @ModelAttribute("event") Event event) {
			User currentUser = userService.findByUsername(principal.getName());
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("eventsInState", eventService.findEventsByState(currentUser.getState()));
			model.addAttribute("eventsOutOfState", eventService.findEventsNotByState(currentUser.getState()));
			model.addAttribute("states", states);
			return "NewFile.jsp"; 
	}
	
	@PostMapping("/events")
	public String createEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, 
			Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		model.addAttribute("eventsInState", eventService.findEventsByState(currentUser.getState()));
		model.addAttribute("eventsOutOfState", eventService.findEventsNotByState(currentUser.getState()));
		model.addAttribute("states", states);
		if (result.hasErrors()) {
			return "NewFile.jsp";
		} else {
			event.setHost(currentUser);
			eventService.createEvent(event);
			return "redirect:/events";
		}
	}
	
	@GetMapping("/events/{id}")
	public String eventPage(@PathVariable("id") Long id, Model model, @Valid @ModelAttribute("post") Post post) {
		Event event = eventService.findEventById(id);
		model.addAttribute("event", event);
		model.addAttribute("posts", postService.findPostOfEvent(event));
		return "schedule.jsp";
	}
	
	@PostMapping("/events/{id}/newpost")
	public String newPost(@PathVariable("id") Long id, Model model, Principal principal, 
			@Valid @ModelAttribute("post") Post post, BindingResult result) {
		Event event = eventService.findEventById(id);
		model.addAttribute("event", event);
		model.addAttribute("posts", postService.findPostOfEvent(event));
		if (result.hasErrors()) {
			return "schedule.jsp";
		} else {
			User currentUser = userService.findByUsername(principal.getName());
			post.setAuthor(currentUser);
			post.setEvent(event);
			postService.createPost(post);
			return "redirect:/events/" + id;
		}
	}
	
	@GetMapping("/events/{id}/join")
	public String joinEvent(@PathVariable("id") Long id, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		Event event = eventService.findEventById(id);
		if (event.getHost() != currentUser) {
			List<User> attendees = event.getAttendees();
			attendees.add(currentUser);
			event.setAttendees(attendees);
			eventService.createEvent(event);
		}
		return "redirect:/events";
	}
	
	@GetMapping("/events/{id}/cancel")
	public String unjoinEvent(@PathVariable("id") Long id, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		Event event = eventService.findEventById(id);
		if (event.getHost() != currentUser) {
			List<User> attendees = event.getAttendees();
			attendees.remove(currentUser);
			event.setAttendees(attendees);
			eventService.createEvent(event);
		}
		return "redirect:/events";
	}
	
	@GetMapping("/events/{id}/edit")
	public String editEvent(@PathVariable("id") Long id, Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		Event event = eventService.findEventById(id);
		if (event.getHost() != currentUser || event == null) {
			return "redirect:/events";
		} else {
			model.addAttribute("states", states);
			model.addAttribute("event", event);
			return "edit.jsp";
		}
	}
	
	@PostMapping("/events/{id}/edit")
	public String updateEvent(@PathVariable("id") Long id, Model model,
			@Valid @ModelAttribute("event") Event event, BindingResult result) {
		model.addAttribute("states", states);
		if (result.hasErrors()) {
			return "edit.jsp";
		} else {
			Event currentEvent = eventService.findEventById(id);
			currentEvent.setName(event.getName());
			currentEvent.setDate(event.getDate());
			currentEvent.setCity(event.getCity());
			currentEvent.setState(event.getState());
			eventService.createEvent(currentEvent);
			return "redirect:/events/" + id;
		}
	}
	
	@GetMapping("/events/{id}/delete")
	public String deleteEvent(@PathVariable("id") Long id, Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		Event event = eventService.findEventById(id);
		if (event.getHost() == currentUser && event != null) {
			eventService.deleteEvent(id);
		}
		return "redirect:/events";
	}
}