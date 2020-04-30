package ru.javadev;


import ru.javadev.domain.Cart;
import ru.javadev.domain.Product;
import ru.javadev.ejb.CartManagerBean;
import ru.javadev.ejb.ProductManagerBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class CartBean implements Serializable {
    private Cart cart;
    private Product product;
    private List<Product> products;
    private String name;
    private int price;
    private int quantity;

    @EJB
    CartManagerBean cartManagerBean;

    @EJB
    ProductManagerBean productManagerBean;

    @PostConstruct
    public void init() {
        products = productManagerBean.getProduct();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void createCart() {
        if(cart == null) {
            cart = cartManagerBean.createCart();
        }
    }

    public void createProduct() {
        productManagerBean.createProduct(name, price, quantity);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        if(cart == null) {
            return;
        }
        cartManagerBean.addToCart(product.getId(), cart.getId());
    }

    public List<Product> getProductInCart() {
        if(cart == null) {
            return Collections.emptyList();
        }

        return cartManagerBean.getProductInCart(cart.getId());
    }
}
