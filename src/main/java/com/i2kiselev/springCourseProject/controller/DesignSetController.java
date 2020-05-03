package com.i2kiselev.springCourseProject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import com.i2kiselev.springCourseProject.resource.RollSetModel;
import com.i2kiselev.springCourseProject.resource.RollSetModelAssembler;
import com.i2kiselev.springCourseProject.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/design",produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class DesignSetController {

    private final RollSetRepository setRepository;

    private final UserService userService;

    public DesignSetController(ObjectMapper objectMapper, RollSetRepository setRepository, UserService userService) {
        this.setRepository = setRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public @ResponseBody Iterable<User> users(){
        return userService.findAll();
    }

    @GetMapping("/test")
    public String test(){
        return "test string";
    }

    @GetMapping("/recent")
    public CollectionModel<RollSetModel> recentSets() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        List<RollSet> rollSets = setRepository.findAll(page).getContent();
        CollectionModel<RollSetModel> recentResources = new RollSetModelAssembler().toCollectionModel(rollSets);
        recentResources.add(
                linkTo(methodOn(DesignSetController.class).recentSets())
                        .withRel("recents"));
        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RollSet> setById(@PathVariable("id") Long id) {
        Optional<RollSet> optSet = setRepository.findById(id);
        return optSet.map(rollSet -> new ResponseEntity<>(rollSet, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RollSet postSet(@RequestBody RollSet rollSet) {
        return setRepository.save(rollSet);
    }
}
