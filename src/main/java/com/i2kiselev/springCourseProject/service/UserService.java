package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            log.info("Returned user: "+username);
            return user;
        }
        log.warn("User '" + username + "' not found");
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }
    String getCurrentUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    User getCurrentUser() {
        String username = getCurrentUsername();
        User user = userRepo.findByUsername(username);
        if (user != null) {
            log.info("Returned current app user: "+username);
            return user;
        }
        log.warn("User '" + username + "' not found");
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }
}
