package com.i2kiselev.springCourseProject.model;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RollSetForm {
    private String name;

    private Integer price;

    private String description;

    private MultipartFile file;

    private List<Long> id;

    private List<Long> quantity;
}
