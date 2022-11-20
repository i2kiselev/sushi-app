package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Slf4j
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartService {

    private final UserService userService;

    private final OrderService orderService;

    private final Map<AbstractProduct, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartService(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    public void addProduct(AbstractProduct product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        log.info("Product " + product.getName() + " was added to cart");
    }


    public void removeProduct(AbstractProduct product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
        log.info("Product " + product.getName() + " was removed from cart");
    }


    public Map<AbstractProduct, Integer> getProductsInCart() {
        log.info("Returned map of current cart contents");
        return Collections.unmodifiableMap(products);
    }

    private List<AbstractProduct> getListOfProducts() {
        List<AbstractProduct> result = new ArrayList<>();
        for (Map.Entry<AbstractProduct, Integer> entry : getProductsInCart().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        }
        log.info("Returned list of products from map");
        return result;
    }

    public void checkout() {
        Order order = getOrderBeforeCheckout();
        orderService.setTotal(order);
        orderService.saveOrder(order);
        products.clear();
        log.info("Checkout performed on order №" + order.getId());
    }

    public Boolean isProductsEmpty() {
        return products.isEmpty();
    }

    public Long getTotal() {
        long total = 0L;
        for (Map.Entry<AbstractProduct, Integer> entry : products.entrySet()) {
            total += (long) entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    private Order getOrderBeforeCheckout() {
        log.info("Returned order prepared for checkout");
        return new Order(userService.getCurrentUser(), Order.Status.ACCEPTED, getListOfProducts());
    }
}
