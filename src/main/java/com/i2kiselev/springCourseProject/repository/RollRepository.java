package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Roll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RollRepository extends PagingAndSortingRepository<Roll,Long> {
}
