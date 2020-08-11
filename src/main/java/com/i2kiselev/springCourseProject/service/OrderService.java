package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.exception.NoEntityException;
import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.AttributesStrategy;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.OrderRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public Order findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            log.info("Returned order with id "+id);
            return optionalOrder.get();
        }
        log.info("Order with id "+id+"not found");
        throw new NoEntityException("Order with id "+id+" not found");

    }

    public Page<Order> getUnfinishedOrders(Pageable pageable){
        log.info("Returned paged unfinished orders");
        return orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, pageable);
    }
    public Page<Order> getOrdersForCurrentUser(Pageable pageable){
        User currentUser = userService.getCurrentUser();
        log.info("Returned list of orders for user "+currentUser.getUsername());
        return orderRepository.findAllByUser(currentUser, pageable);

    }

    public void nextStatus(Long orderId){
        Order order =  this.findById(orderId);
        order.nextStatus();
        log.info("Status changed for order №"+order.getId());
        saveOrder(order);
    }

    void setTotal(Order order){
        long total =0L;
        for (AttributesStrategy product : order.getItems()){
            total+=product.getFinalCost();
        }
        order.setTotal(total);
        log.info("Calculated total cost for order №"+order.getId());
    }

    void saveOrder(Order order){
        log.debug("Saved order №"+order.getId());
        orderRepository.save(order);
    }
}
