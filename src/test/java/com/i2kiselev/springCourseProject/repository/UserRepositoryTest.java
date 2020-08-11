package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.exception.NoEntityException;
import com.i2kiselev.springCourseProject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findById(){
        User test = new User("test");
        userRepository.save(test);
        User user = userRepository.findById(test.getId()).get();
        assertThat(user.getUsername()).isEqualTo("test");
    }

    @Test
    void findByUsername(){
        userRepository.save(new User("admin"));
        User user = userRepository.findByUsername("admin");
        assertThat(user.getUsername()).isEqualTo("admin");
    }

    @Test
    void findAll(){
        User admin = new User("admin");
        User user = new User("user");
        userRepository.save(admin);
        userRepository.save(user);
        Iterable<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        assertThat(userList.get(0).getUsername()).isEqualTo("admin");
        assertThat(userList.get(1).getUsername()).isEqualTo("user");
    }

}
