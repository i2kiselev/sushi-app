package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserService {
     Iterable<User> findAll();
     Optional<User> findById(Long id);
     void deleteUserById(Long id);
}
