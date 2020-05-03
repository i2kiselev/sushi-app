package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RollSetRepository extends PagingAndSortingRepository<RollSet,Long> {

}
