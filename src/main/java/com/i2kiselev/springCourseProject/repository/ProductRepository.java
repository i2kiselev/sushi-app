package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
}
