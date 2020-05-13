package ru.javadev.auth.ejb;

import ru.javadev.auth.domain.User;
import ru.javadev.domain.Product;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class UserManagerBean {

    @PersistenceContext(unitName = "shopPU")
    private EntityManager entityManager;

    public User createUser(String login, String password, String city, String email, String number, boolean admin) { //создание сущности user с данными
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setCity(city);
        user.setEmail(email);
        user.setNumber(number);
        user.setAdmin(admin);

        entityManager.persist(user);

        return user;
    }

    public List<User> getUsers() {      //получение всех сущностей user
        return entityManager.createQuery("select u from Users u", User.class).getResultList();
    }

    public User getUser(String login) {       //получение сущности user по логину
        return (User)entityManager.createQuery("select u from Users u where u.login = :login").setParameter("login", login).getResultList().get(0);
    }

    public User editUser(User user) {
        entityManager.merge(user);

        return user;
    }

    public User deleteUser(long id) {
        User user2 = entityManager.find(User.class, id);
        entityManager.remove(user2);

        return user2;
    }

    public int CheckLogin(String login) {           //проверка существования сущности user по логину
        List<User> logins = entityManager.createQuery("select u from Users u where u.login = :login").setParameter("login", login).getResultList();
        return logins.size();
    }

    public boolean CheckPassword(String login, String password) {     //проверка существования пароля в сущности user по логину
        List<User> logins = entityManager.createQuery("select u from Users u where u.login = :login").setParameter("login", login).getResultList();
        return logins.get(0).getPassword().equals(password);
    }

    public int CheckNumber(String number) {
        List<User> numbers = entityManager.createQuery("select u from Users u where u.number = :number").setParameter("number", number).getResultList();
        return numbers.size();
    }

    public int CheckEmail(String email) {
        List<User> emails = entityManager.createQuery("select u from Users u where u.email = :email").setParameter("email", email).getResultList();
        return emails.size();
    }

    public boolean AdminStatus(String login) {      //проверка статуса админа по логину
        List<User> adminstatus = entityManager.createQuery("select u from Users u where u.login = :login").setParameter("login", login).getResultList();
        return adminstatus.get(0).isAdmin();
    }
}
