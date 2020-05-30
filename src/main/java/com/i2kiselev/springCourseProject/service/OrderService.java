package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.AttributesStrategy;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }
    public Order findById(Long id){
        return orderRepository.findById(id).get();
    }
    public Page<Order> getUnfinishedOrders(Pageable pageable){
        return orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, pageable);
    }
    public Page<Order> getOrdersForCurrentUser(Pageable pageable){
        return orderRepository.findAllByUser(userService.getCurrentUser(), pageable);
    }
    public void nextStatus(Long orderId){
        Order order =  findById(orderId);
        order.nextStatus();
        saveOrder(order);
    }
    public void setTotal(Order order){
        long total =0L;
        for (AttributesStrategy product : order.getItems()){
            total+=product.getFinalCost();
        }
        order.setTotal(total);
    }
}
