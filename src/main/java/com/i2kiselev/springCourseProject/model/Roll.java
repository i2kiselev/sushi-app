package com.i2kiselev.springCourseProject.model;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("1")
public class Roll extends AbstractProduct implements Serializable, AttributesStrategy {
    @Column(name = "composition")
    private String composition;

    @Override
    public String toString() {
        return super.toString();
    }
}
