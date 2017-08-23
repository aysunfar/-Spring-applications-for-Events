package com.aysun.belt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aysun.belt.models.Event;
import com.aysun.belt.models.Post;


@Repository
public interface PostRepo extends CrudRepository<Post, Long> {
	List<Post> findByEventOrderByCreatedAtAsc(Event event);
}

