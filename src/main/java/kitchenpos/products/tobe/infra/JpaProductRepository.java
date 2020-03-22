package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product save (Product product){
        em.persist(product);
        Product savedProduct = findById(product.getId())
            .orElseThrow(() -> new RuntimeException("저장 실패"));
        return savedProduct;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public List<Product> list () {
        return em.createQuery("select * from Product p").getResultList();
    }

    @Override
    public boolean findByName(String name) {
        List<Product> products = em.createQuery("select * from Product p where p.name = :name", Product.class)
            .setParameter("name", name)
            .getResultList();
        return products.isEmpty() ? true : false;
    }
}
