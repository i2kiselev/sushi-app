package com.i2kiselev.springCourseProject.resource;

import com.i2kiselev.springCourseProject.controller.DesignSetController;
import com.i2kiselev.springCourseProject.model.RollSet;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class RollSetModelAssembler extends RepresentationModelAssemblerSupport<RollSet, RollSetModel> {

    public RollSetModelAssembler() {
        super(DesignSetController.class, RollSetModel.class);
    }

    @Override
    public RollSetModel toModel(RollSet entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected RollSetModel instantiateModel(RollSet entity) {
        return new RollSetModel(entity);
    }

}
