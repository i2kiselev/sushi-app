package com.i2kiselev.springCourseProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class RollSet extends AbstractProduct implements Serializable {

    @Column(name = "createdAt")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(targetEntity = Roll.class)
    @Size(min=10,message = "You must choose at least 20 rolls to create set")
    private List<Roll> rolls;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = new Date();
    }
}
