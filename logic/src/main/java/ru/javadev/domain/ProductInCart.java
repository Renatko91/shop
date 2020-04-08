package ru.javadev.domain;

import javax.persistence.*;

@Entity
public class ProductInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int quantityInCart;

    @ManyToOne
    private Product product;
    @ManyToOne
    private Cart cart;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
