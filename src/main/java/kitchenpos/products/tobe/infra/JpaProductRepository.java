package kitchenpos.products.tobe.infra;

import kitchenpos.common.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProductEntity save (ProductEntity productEntity){
        em.persist(productEntity);
        ProductEntity savedProductEntity = findById(productEntity.getId())
            .orElseThrow(() -> new RuntimeException("저장 실패"));
        return savedProductEntity;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return Optional.ofNullable(em.find(ProductEntity.class, id));
    }

    @Override
    public List<ProductEntity> list () {
        return em.createQuery("select * from ProductEntity p").getResultList();
    }

    @Override
    public boolean findByName(String name) {
        List<ProductEntity> productEntities = em.createQuery("select * from ProductEntity p where p.name = :name", ProductEntity.class)
            .setParameter("name", name)
            .getResultList();
        return productEntities.isEmpty() ? true : false;
    }

    public BigDecimal findProductPriceById (Long id){
        Number result = (Number) em.createQuery("select p.price from ProductEntity p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        return new BigDecimal(result.toString());
    }
}
