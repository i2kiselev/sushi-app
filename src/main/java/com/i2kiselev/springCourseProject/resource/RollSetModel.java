package com.i2kiselev.springCourseProject.resource;

import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Relation(value = "rollSet", collectionRelation = "rollSets")
@Getter
public class RollSetModel  extends RepresentationModel<RollSetModel> {

    @Getter(AccessLevel.NONE)
    private static final RollModelAssembler rollAssembler = new RollModelAssembler();

    private String name;

    private Integer price;

    private Integer weight;

    private Date createdAt;

    private User createdBy;

    private CollectionModel<RollModel> rolls;

    public RollSetModel(RollSet rollSet){
        this.name = rollSet.getName();
        this.price = rollSet.getPrice();
        this.weight = rollSet.getWeight();
        this.createdAt = rollSet.getCreatedAt();
        this.createdBy = rollSet.getUser();
        this.rolls = rollAssembler.toCollectionModel(rollSet.getRolls());

    }
}
