package com.i2kiselev.springCourseProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placeDate")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum Status{
        ACCEPTED,
        IN_KITCHEN,
        DELIVERING,
        FINISHED;
        private static Status[] vals = values();
        public Status next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }

    @Column(name = "cost")
    private Long total;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<AbstractProduct> items;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = new Date();
    }

    public Order(User user, Status status, List<AbstractProduct> items) {
        this.user = user;
        this.status = status;
        this.items = items;
    }
    public Order(Long id){
        this.id=id;
    }

    public Order(Status status) {
        this.status = status;
    }

    public Order(Status status, User user) {
        this.status = status;
        this.user = user;

    }
    public Order(Long id, User user) {
        this.user = user;
    }

    public void nextStatus(){
        if(this.getStatus()!=Status.FINISHED) this.setStatus(getStatus().next());
    }
}
