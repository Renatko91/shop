package ru.javadev.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int price;

    @OneToMany (mappedBy = "product")
    private List<ProductInCart> productInCart;

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

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<ProductInCart> getProductInCart() {
        return productInCart;
    }

    public void setProductInCart(List<ProductInCart> productInCart) {
        this.productInCart = productInCart;
    }
}
