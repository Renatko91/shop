package ru.javadev;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import ru.javadev.auth.domain.User;
import ru.javadev.auth.ejb.UserManagerBean;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserLoginView implements Serializable {
    private String login;
    private String password;
    private String city;
    private String email;
    private String number;
    User user;
    private List<User> users;

    @EJB
    UserManagerBean userManagerBean;

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

    public void login() {
        FacesMessage message = null;
        boolean loggedIn = false;

        if(userManagerBean.CheckLogin(login) == 0) {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка", "Логин не существует");
        } else if(!userManagerBean.CheckPassword(login, password)) {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка", "Пароль не существует");
        } else {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Добро пожаловать", login);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void registration() {
        FacesMessage message = null;
        boolean regedIn = false;

        if(userManagerBean.CheckLogin(login) != 0) {
            regedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка", "Логин существует");
        } else if(userManagerBean.CheckEmail(email) != 0) {
            regedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка", "Емайл существует");
        } else if(userManagerBean.CheckNumber(number) != 0) {
            regedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка", "Номер телефона существует");
        } else {
            regedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Добро пожаловать", login);
            userManagerBean.createUser(login, password, city, email, number, false);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext.getCurrentInstance().addCallbackParam("regedIn", regedIn);
    }
}