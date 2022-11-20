package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
