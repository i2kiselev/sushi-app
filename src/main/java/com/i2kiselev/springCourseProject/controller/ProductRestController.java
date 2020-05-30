package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
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

}
