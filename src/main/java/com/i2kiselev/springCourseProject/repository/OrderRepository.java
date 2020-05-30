package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
    Page<Order> findAllByStatusIsNot(Order.Status status, Pageable pageable);
    Page<Order> findAllByUser(User user, Pageable pageable);
}
