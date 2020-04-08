package ru.javadev.ejb;

import ru.javadev.domain.Cart;
import ru.javadev.domain.Product;
import ru.javadev.domain.ProductInCart;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class CartManagerBean {

    @PersistenceContext(unitName = "shopPU")
    private EntityManager entityManager;

    public Cart createCart() {
        Cart cart = new Cart();
        entityManager.persist(cart);
        return cart;
    }

    public boolean addToCart(long productId, long cartId, int needQuantity) {
        Product product = entityManager.find(Product.class, productId);
        if(product == null) {
            return false;
        }

        Cart cart = entityManager.find(Cart.class, cartId);
        if(cart == null) {
            return false;
        }

        ProductInCart productInCart = new ProductInCart();
        productInCart.setCart(cart);
        productInCart.setProduct(product);
        productInCart.setQuantityInCart(needQuantity);
        entityManager.persist(productInCart);

        return true;
    }

    public List<Product> getProductInCart(long cartId) {
        Cart cart = entityManager.find(Cart.class, cartId);
        if(cart == null) {
            return Collections.emptyList();
        }

        List<ProductInCart> productInCarts = cart.getProductInCart();
        ArrayList<Product> result = new ArrayList<>();
        for(ProductInCart productInCart : productInCarts) {
            result.add(productInCart.getProduct());
        }
        return result;
    }
}
