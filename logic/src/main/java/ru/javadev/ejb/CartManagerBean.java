package ru.javadev.ejb;

import ru.javadev.auth.domain.User;
import ru.javadev.domain.Cart;
import ru.javadev.domain.CartInUser;
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

    @PersistenceContext(unitName = "shopPU")        //установка маркера для обращения к базе данных shop из persistence
    private EntityManager entityManager;

    public Cart createCart() {
        Cart cart = new Cart();
        entityManager.persist(cart);
        return cart;
    }

    public boolean addToCart(long productId, long cartId, int quantity) {    //добавление продукта в сущность ProductInCart
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
        productInCart.setQuantity(quantity);
        entityManager.persist(productInCart);

        return true;
    }

    public boolean addUserCart(long cartId, long userId, int size, int sum) {    //добавление корзины и пользователя в сущность CartInUser
        Cart cart = entityManager.find(Cart.class, cartId);
        if (cart == null) {
            return false;
        }

        User user = entityManager.find(User.class, userId);
        if (user == null) {
            return false;
        }

        CartInUser cartInUser = new CartInUser();
        cartInUser.setUser(user);
        cartInUser.setCart(cart);
        cart.setSize(size);
        cart.setSum(sum);
        entityManager.persist(cartInUser);
        entityManager.merge(cart);

        return true;
    }

    public List<Cart> getCartInUser(long userId) {        //получение корзины для пользователя из сущности CartInUser
        User user = entityManager.find(User.class, userId);
        if(user == null) {
            return Collections.emptyList();
        }

        List<CartInUser> cartInUsers = user.getCartInUsers();
        ArrayList<Cart> result = new ArrayList<>();
        for(CartInUser cartInUser : cartInUsers) {
            result.add(cartInUser.getCart());
        }
        return result;
    }
}
