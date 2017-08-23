package com.aysun.belt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aysun.belt.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	User findByUsername(String username);
}