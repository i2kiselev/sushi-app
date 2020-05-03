package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AbstractProductRepository extends CrudRepository<AbstractProduct, Long> {

}
