package com.i2kiselev.springCourseProject;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.tools.jar.CommandLine;

@SpringBootApplication
public class SpringCourseProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringCourseProjectApplication.class, args);
	}


}