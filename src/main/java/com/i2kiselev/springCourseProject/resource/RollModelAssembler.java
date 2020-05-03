package com.i2kiselev.springCourseProject.resource;

import com.i2kiselev.springCourseProject.controller.ProductController;
import com.i2kiselev.springCourseProject.model.Roll;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class RollModelAssembler extends RepresentationModelAssemblerSupport<Roll,RollModel> {

    public RollModelAssembler() {
        super(ProductController.class, RollModel.class);
    }

    @Override
    public RollModel toModel(Roll entity) {
        return createModelWithId(entity.getId(), entity);

    }
    @Override
    protected RollModel instantiateModel(Roll entity) {
        return new RollModel(entity);
    }
}
