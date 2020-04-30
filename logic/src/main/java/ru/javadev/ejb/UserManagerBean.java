package ru.javadev.ejb;

import ru.javadev.domain.Product;
import ru.javadev.domain.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserManagerBean {

    @PersistenceContext(unitName = "shopPU")
    private EntityManager entityManager;

    public User createUser(String login, String password, boolean admin) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAdmin(admin);
        entityManager.persist(user);

        return user;
    }
}
