package com.i2kiselev.springCourseProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractProduct implements Serializable,AttributesStrategy {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "weight")
    private Integer weight;

    @Column(name ="image")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] image;

    @Column(name ="description")
    private String description;

    @Transient
    @JsonIgnore
    private MultipartFile file;

    @Override
    public String toString() {
        return  " Name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
    @JsonIgnore
    @Override
    public Integer getFinalCost() {
        return price;
    }

    @JsonIgnore
    @Override
    public Integer getFinalWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProduct)) return false;
        AbstractProduct product = (AbstractProduct) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
