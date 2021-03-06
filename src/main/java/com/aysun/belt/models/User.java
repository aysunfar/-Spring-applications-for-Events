package com.aysun.belt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import com.aysun.belt.models.Event;
import com.aysun.belt.models.Post;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Column
    @Size(min=3)
	private String username;
	@Column
    @Size(min=3)
	private String firstName;
	@Column
    @Size(min=3)
	private String lastName;
	@Column
	@Size(min=2)
	private String city;
	private String state;
	@Column
	@Size(min=5)
	private String password;
	@Transient
	private String passwordConfirmation;
	@Column
    @DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date createdAt;
	@Column
    @DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date updatedAt;
	
	@OneToMany(mappedBy="host", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Event> eventsCreated;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="events_attendees",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="events_id")
	)
	private List<Event> eventsAttending;
	
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> posts;
	
	public User() {}
	
	public User(String username, String firstName, String lastName, String city, String state, String password) {
		this.username = username; 
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.password = password;
	}
	
	@PrePersist
	protected void onCreate() {
		this.setCreatedAt(new Date());
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.setUpdatedAt(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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

	public void setEventsCreated(List<Event> eventsCreated) {
		this.eventsCreated = eventsCreated;
	}

	public List<Event> getEventsAttending() {
		return eventsAttending;
	}

	public void setEventsAttending(List<Event> eventsAttending) {
		this.eventsAttending = eventsAttending;
	}

	public List<Post> getPots() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
