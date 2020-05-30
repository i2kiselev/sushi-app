package com.i2kiselev.springCourseProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("1")
public class Roll extends AbstractProduct implements Serializable {

    @Column(name = "composition")
    private String composition;

}
