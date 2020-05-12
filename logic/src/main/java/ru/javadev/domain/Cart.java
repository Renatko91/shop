package ru.javadev.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int size;
    private int sum;

    @OneToMany (mappedBy = "cart")
    private List<CartInUser> cartInUsers;

    @OneToMany (mappedBy = "cart")
    private List<ProductInCart> productInCart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<ProductInCart> getProductInCart() {
        return productInCart;
    }

    public void setProductInCart(List<ProductInCart> productInCart) {
        this.productInCart = productInCart;
    }

    public List<CartInUser> getCartInUsers() {
        return cartInUsers;
    }

    public void setCartInUsers(List<CartInUser> cartInUsers) {
        this.cartInUsers = cartInUsers;
    }
}
