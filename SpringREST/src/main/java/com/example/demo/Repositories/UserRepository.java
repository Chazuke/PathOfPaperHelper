package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.User;

public interface UserRepository extends CrudRepository<User, String> {
	User findByUsername(String username);
}
