package com.aysun.belt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aysun.belt.models.Post;
import com.aysun.belt.models.Event;

import com.aysun.belt.repositories.PostRepo;

@Service
public class PostService {
	
	private PostRepo postRepo;
	
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
	
	public void createPost(Post post) {
		postRepo.save(post);
	}
	
	public List<Post> findPostOfEvent(Event event) {
		return postRepo.findByEventOrderByCreatedAtAsc(event);
	}
}