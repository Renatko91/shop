package ru.javadev.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany (mappedBy = "cart")
    private List<ProductInCart> productInCart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductInCart> getProductInCart() {
        return productInCart;
    }

    public void setProductInCart(List<ProductInCart> productInCart) {
        this.productInCart = productInCart;
    }
}
