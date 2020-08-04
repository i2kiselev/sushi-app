package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.service.OrderService;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ProductRestController {

    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public ProductRestController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/allRolls")
    public @ResponseBody Iterable<Roll> allRolls(){
        return productService.findAllRolls();
    }

    @GetMapping("/allProducts")
    public @ResponseBody Iterable<Product> allProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/allRollSets")
    public @ResponseBody Iterable<RollSet> allRollSets(){return productService.findAllRollSets();}

    //Method for displaying pictures directly into HTML
    @GetMapping(path = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE )
    public @ResponseBody byte[] getProductImage(@RequestParam("id") Long productId){
        AbstractProduct product = productService.findById(productId);
        return product.getImage();
    }

    @GetMapping("/orders")
    public @ResponseBody Order getOrderById(@RequestParam("id") Long orderId){
        return orderService.findById(orderId);
    }

}
