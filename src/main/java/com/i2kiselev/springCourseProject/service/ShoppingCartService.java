package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartService {

    private final UserService userService;

    private final OrderService orderService;

    private Map<AbstractProduct, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartService( OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    public void addProduct(AbstractProduct product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }


    public void removeProduct(AbstractProduct product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }


    public Map<AbstractProduct, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    private List<AbstractProduct> getListOfProducts(){
        List<AbstractProduct> result = new ArrayList<>();
        for (Map.Entry<AbstractProduct,Integer> entry: getProductsInCart().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        }
        return result;
    }


    public void checkout()  {
            Order order = getOrderBeforeCheckout();
            orderService.setTotal(order);
            orderService.saveOrder(order);
            products.clear();
    }

    public Boolean isProductsEmpty(){
        return products.isEmpty();
    }

    public Long getTotal() {
        long total = 0L;
        for (Map.Entry<AbstractProduct,Integer> entry: products.entrySet()) {
                total+=entry.getKey().getPrice()*entry.getValue();
        }
        return total;
    }

    private Order getOrderBeforeCheckout(){
        return new Order(userService.getCurrentUser(), Order.Status.ACCEPTED,getListOfProducts());
    }
}
