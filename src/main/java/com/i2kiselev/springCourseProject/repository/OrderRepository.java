package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.Status;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
    Iterable<Order> findAllByStatusIsNot(Status status);
}
