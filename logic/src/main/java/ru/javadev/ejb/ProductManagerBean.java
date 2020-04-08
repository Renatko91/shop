package ru.javadev.ejb;

import ru.javadev.domain.Product;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class ProductManagerBean {

    @PersistenceContext(unitName = "shopPU")
    private EntityManager entityManager;

    public Product createProduct(String name, int price, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        entityManager.persist(product);

        return product;
    }

    public List<Product> getProduct() {
        TypedQuery<Product> query = entityManager.createQuery("select c from Product c", Product.class);
        return query.getResultList();
    }
}
