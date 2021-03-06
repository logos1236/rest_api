package ru.armishev.rest_api.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="product")
@SecondaryTable(name = "price", pkJoinColumns=@PrimaryKeyJoinColumn(name="product_id", referencedColumnName="id"))
public class Product {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(table= "price", name="val")
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
