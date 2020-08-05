package com.i2kiselev.springCourseProject.model;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
@RequiredArgsConstructor
public class Product extends AbstractProduct implements AttributesStrategy {
}
