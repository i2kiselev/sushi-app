package com.i2kiselev.springCourseProject.resource;

import com.i2kiselev.springCourseProject.model.Roll;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class RollModel extends RepresentationModel<RollModel> {

    private String name;

    private Integer price;

    private Integer weight;

    private String composition;

    public RollModel(Roll roll){
        this.name = roll.getName();
        this.price = roll.getPrice();
        this.weight = roll.getWeight();
        this.composition = roll.getComposition();
    }
}
