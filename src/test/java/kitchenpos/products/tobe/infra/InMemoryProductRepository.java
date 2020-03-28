package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private Map<Long, ProductEntity> entities = new HashMap<>();

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        ProductEntity savedProductEntity = new ProductEntity.Builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .price(productEntity.getPrice())
            .build();

        entities.put(savedProductEntity.getId(), savedProductEntity);

        return savedProductEntity;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<ProductEntity> list() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public boolean findByName(String name) {
        List<ProductEntity> productEntities = this.list();

        for(ProductEntity productEntity : productEntities){
            if(name.equals(productEntity.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public BigDecimal findProductPriceById(Long id) {
        ProductEntity findProductEntity = this.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("상품이 없습니다."));

        return findProductEntity.getPrice();
    }
}
