package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/products")
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

    @RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
    public void getImage(@RequestParam("id") Long itemId, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        AbstractProduct product = productService.findById(itemId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(product.getImage());
        response.getOutputStream().close();
    }
}
