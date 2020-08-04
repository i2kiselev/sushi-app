package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.exception.NoEntityException;
import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.repository.AbstractProductRepository;
import com.i2kiselev.springCourseProject.repository.ProductRepository;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RollRepository rollRepository;
    private final RollSetRepository rollSetRepository;
    private final AbstractProductRepository abstractProductRepository;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, RollRepository rollRepository, RollSetRepository rollSetRepository, AbstractProductRepository abstractProductRepository, UserService userService) {
        this.productRepository = productRepository;
        this.rollRepository = rollRepository;
        this.rollSetRepository = rollSetRepository;
        this.abstractProductRepository = abstractProductRepository;
        this.userService = userService;
    }

    private Roll findRollById(Long id) {
        Optional<Roll> optionalRoll = rollRepository.findById(id);
        if (optionalRoll.isPresent()) {
            log.info("Returned roll with id "+id);
            return optionalRoll.get();
        }
        log.info("Roll with id "+id+"not found");
        throw new NoEntityException("Roll with id "+id+" not found");
    }

    public AbstractProduct findById(Long id){
        Optional<AbstractProduct> optionalAbstractProduct = abstractProductRepository.findById(id);
        if (optionalAbstractProduct.isPresent()) {
            log.info("Returned product with id "+id);
            return optionalAbstractProduct.get();
        }
        log.info("Product with id "+id+"not found");
        throw new NoEntityException("Product with id "+id+" not found");
    }

    public void saveProduct(Product product) {
        logProductSave(product);
        productRepository.save(product);    }

    public void saveRoll(Roll roll) {
        logProductSave(roll);
        rollRepository.save(roll);
    }

    public void saveRollSet(RollSet rollSet) {
        logProductSave(rollSet);
        rollSetRepository.save(rollSet);
    }

    public Iterable<AbstractProduct> findAll() {
        logFindProducts(AbstractProduct.class);
        return abstractProductRepository.findAll();
    }

    public Iterable<Product> findAllProducts() {
        logFindProducts(Product.class);
        return productRepository.findAll();
    }

    public Iterable<Roll> findAllRolls() {
        logFindProducts(Roll.class);
        return rollRepository.findAll();
    }

    public Iterable<RollSet> findAllRollSets() {
        logFindProducts(RollSet.class);
        return rollSetRepository.findAll();
    }

    public Iterable<RollSet> findAllRollSetsByStaff(){
        log.info("Returned all roll sets created by staff");
        return rollSetRepository.findAllByStaff();
    }

    public Iterable<RollSet> findAllRollsetsByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        log.info("Returned all roll sets created by user "+ currentUser.getUsername());
        return rollSetRepository.findAllByUser(currentUser);
    }

    public void saveImage(AbstractProduct product) {
        try {
            product.setImage(product.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Image of product "+ product.getName()+"was not set");
        }
    }

    public RollSet getRollSet(RollSetForm form) {
        RollSet rollSet = new RollSet();
        rollSet.setName(form.getName());
        rollSet.setPrice(form.getPrice());
        rollSet.setDescription(form.getDescription());
        rollSet.setFile(form.getFile());
        ArrayList<Roll> finalArray = new ArrayList<>();
        for (Long id : form.getId()){
            for (int i=1 ; i <=form.getQuantity().size(); i++) {
                finalArray.add(findRollById(id));
            }
        }
        rollSet.setRolls(finalArray);
        rollSet.setWeight(rollSet.getFinalWeight());
        rollSet.setUser(userService.getCurrentUser());
        log.info("Returned roll set created from form");
        return rollSet;
    }

    private void logProductSave(AbstractProduct product){
        log.info("Product "+product.getName()+" was saved");
    }

    private void logFindProducts(Class cl){
        log.info("Returned product collection of type "+ cl.getSimpleName());
    }
}
