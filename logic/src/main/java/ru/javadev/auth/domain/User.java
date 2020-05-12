package ru.javadev.auth.domain;

import ru.javadev.domain.CartInUser;
import ru.javadev.domain.ProductInCart;

import javax.persistence.*;
import java.util.List;

@Entity(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    private String password;
    private String city;
    private String number;
    private String email;
    private boolean admin;

    @OneToMany (mappedBy = "user")
    private List<CartInUser> cartInUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<CartInUser> getCartInUsers() {
        return cartInUsers;
    }

    public void setCartInUsers(List<CartInUser> cartInUsers) {
        this.cartInUsers = cartInUsers;
    }
}
