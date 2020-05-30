package com.i2kiselev.springCourseProject.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class Product extends AbstractProduct implements AttributesStrategy {
}
