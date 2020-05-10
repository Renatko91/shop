package ru.javadev;


import org.primefaces.event.RowEditEvent;
import ru.javadev.auth.domain.User;
import ru.javadev.auth.ejb.UserManagerBean;
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
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private User user;
    private List<User> users;
    private String login;
    private String password;
    private String city;
    private String email;
    private String number;
    private boolean admin;

    @EJB
    UserManagerBean userManagerBean;

    @PostConstruct
    public void init() {
        users = userManagerBean.getUsers();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserManagerBean getUserManagerBean() {
        return userManagerBean;
    }

    public void setUserManagerBean(UserManagerBean userManagerBean) {
        this.userManagerBean = userManagerBean;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void onRowEdit(RowEditEvent event) {
        User editUser = (User)event.getObject();
        userManagerBean.editUser(editUser);
        FacesMessage msg = new FacesMessage("Изменено", String.valueOf(editUser.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Вышел");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onAddNew() {
        User newUser = userManagerBean.createUser(login, password, city, email, number, admin);
        users.add(newUser);
        FacesMessage msg = new FacesMessage("Новый пользователь добавлен", String.valueOf(newUser.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete() {
        users.remove(user);
        userManagerBean.deleteUser(user.getId());
        FacesMessage msg = new FacesMessage("Пользователь удален");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
