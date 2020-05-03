package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/allRolls")
    public @ResponseBody Iterable<Roll> allRolls(){
        return productService.findAllRolls();
    }

    @GetMapping(path = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE )
    public @ResponseBody byte[] getProductImage(@RequestParam("id") Long productId){
        AbstractProduct product = productService.findById(productId);
        return product.getImage();
    }

}
