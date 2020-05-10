package ru.javadev;


import org.primefaces.event.RowEditEvent;
import ru.javadev.domain.Cart;
import ru.javadev.domain.Product;
import ru.javadev.ejb.CartManagerBean;
import ru.javadev.ejb.ProductManagerBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class ProductBean implements Serializable {
    private Cart cart;
    private Product product;
    private List<Product> products;
    private String name;
    private int price;

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

    public void createCart() {
        if(cart == null) {
            cart = cartManagerBean.createCart();
        }
    }

    public void createProduct() {
        productManagerBean.createProduct(name, price);
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

    public void onRowEdit(RowEditEvent event) {
        Product editProduct = (Product)event.getObject();
        productManagerBean.editProduct(editProduct);
        FacesMessage msg = new FacesMessage("Изменено", String.valueOf(editProduct.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Вышел");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onAddNew() {
        Product newProduct = productManagerBean.createProduct(name, price);
        products.add(newProduct);
        FacesMessage msg = new FacesMessage("Новый продукт добавлен", String.valueOf(newProduct.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete() {
        products.remove(product);
        productManagerBean.deleteProduct(product.getId());
        FacesMessage msg = new FacesMessage("Продукт удален");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
