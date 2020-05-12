package ru.javadev;


import org.primefaces.event.RowEditEvent;
import ru.javadev.auth.domain.User;
import ru.javadev.domain.Cart;
import ru.javadev.domain.Product;
import ru.javadev.ejb.CartManagerBean;
import ru.javadev.ejb.ProductManagerBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class ProductBean implements Serializable {
    private Cart cart;
    private Product product;
    private User user;
    private List<Product> products;
    private String name;
    private int price;
    private int quantity;
    private String findName;
    Map<Product, Integer> userCart = new HashMap<>();

    @EJB
    CartManagerBean cartManagerBean;

    @EJB
    ProductManagerBean productManagerBean;

    @Inject
    UserLoginView userLoginView;

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

    public String getFindName() {
        return findName;
    }

    public void setFindName(String findName) {
        this.findName = findName;
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

    public void addProduct() {
        userCart.put(product, quantity);
    }

    public List<Cart> getCartInUser() {
        user = userLoginView.getUser();
        if(user == null) {
            return Collections.emptyList();
        }

        return cartManagerBean.getCartInUser(user.getId());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Map<Product, Integer> getUserCart() {
        return userCart;
    }

    public void setUserCart(Map<Product, Integer> userCart) {
        this.userCart = userCart;
    }

    public int getUserCartSize() {
        int amount = 0;
        for(Map.Entry<Product, Integer> item : userCart.entrySet()) {
            amount = amount + item.getValue();
        }
        return amount;
    }

    public int getUserCartSum() {
        int sum = 0;
        for(Map.Entry<Product, Integer> item : userCart.entrySet()) {
            sum = sum + item.getKey().getPrice() * item.getValue();
        }
        return sum;
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

    public void onAddNew() throws IOException {
        Product newProduct = productManagerBean.createProduct(name, price);
        products.add(newProduct);
        FacesMessage msg = new FacesMessage("Новый продукт добавлен", String.valueOf(newProduct.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
    }

    public void onDelete() throws IOException {
        products.remove(product);
        productManagerBean.deleteProduct(product.getId());
        FacesMessage msg = new FacesMessage("Продукт удален");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
    }

    public void checkout() throws IOException {
        cart = cartManagerBean.createCart();
        user = userLoginView.getUser();
        cartManagerBean.addUserCart(cart.getId(), user.getId(), getUserCartSize(), getUserCartSum());
        for(Map.Entry<Product,Integer> item : userCart.entrySet()) {
            cartManagerBean.addToCart(item.getKey().getId(), cart.getId(), item.getValue());
        }
        userCart.clear();
    }

    public void find() {
        List<Product> result = new ArrayList<>();
        result = productManagerBean.getProduct();
        for(Product p : products) {
            if(p.getName().equals(findName)) {
                result.clear();
                result.add(p);
            }
        }
        products = result;
    }
}
