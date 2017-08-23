package com.aysun.belt.models;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import com.aysun.belt.models.User;
import com.aysun.belt.models.Post;

@Entity
@Table(name="events")
public class Event {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=3)
	private String name;
	@Future(message="Date cannot be a future date!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@Size(min=2)
	private String city;
	
	private String state;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User host;
	
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> posts;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = "events_attendees",
			joinColumns = @JoinColumn(name = "event_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> attendees;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date createdAt;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date updatedAt;
	
	public Event() {}
	
	public Event(String name, Date date, String city, String state) {
		this.name = name;
		this.date = date;
		this.city = city;
		this.state = state;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate(){
		this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    		this.setUpdatedAt(new Date());
    }

}
	
