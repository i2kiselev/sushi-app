package com.i2kiselev.springCourseProject.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class RollSet extends AbstractProduct implements Serializable, AttributesStrategy {

    @Column(name = "createdAt")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public Integer getFinalCost() {
        int total = 0;
        for (Roll roll:this.getRolls()){
            total+=roll.getFinalCost();
        }
        return total;
    }

    @Override
    public Integer getFinalWeight() {
        int total = 0;
        for (Roll roll: this.getRolls()){
            total+=roll.getFinalWeight();
        }
        return total;
    }

    @ManyToMany(targetEntity = Roll.class, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Roll> rolls;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = new Date();
    }


}
