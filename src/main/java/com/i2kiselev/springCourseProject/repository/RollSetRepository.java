package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RollSetRepository extends PagingAndSortingRepository<RollSet,Long> {
    @Query("select rollset from RollSet rollset join rollset.user u where u in (select user from User user join user.roles r where r.role='ROLE_ADMIN')")
    Iterable<RollSet> findAllByStaff();

    Iterable<RollSet> findAllByUser(User user);
}
