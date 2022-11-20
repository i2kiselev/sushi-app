package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Spy
    private UserService userService;

    @Test
    void loadUserByUsername_positive() {
        User user = new User();
        given(userRepository.findByUsername("user")).willReturn(user);
        assertThat(userService.loadUserByUsername("user")).isEqualTo(user);
    }

    @Test
    void loadUserByUsername_noUser() {
        given(userRepository.findByUsername("user")).willReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("user"));
    }

    @Test
    void getCurrentUser_positive() {
        User user = new User();
        doReturn("user").when(userService).getCurrentUsername();
        given(userRepository.findByUsername("user")).willReturn(user);
        assertThat(userService.getCurrentUser()).isEqualTo(user);
    }

    @Test
    void getCurrentUser_noUser() {
        doReturn("user").when(userService).getCurrentUsername();
        given(userRepository.findByUsername("user")).willReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUser());
    }
}
