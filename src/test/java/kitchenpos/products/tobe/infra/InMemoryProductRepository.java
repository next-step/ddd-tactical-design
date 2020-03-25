package kitchenpos.products.tobe.infra;
import kitchenpos.common.Price;
import kitchenpos.products.tobe.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

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
    public List<Price> findAllPrice(List<Long> ids) {
        List<Price> prices = new ArrayList<>();

        ids.stream()
            .forEach(id -> {
                ProductEntity productEntity = this.findById(id).orElseThrow(() ->
                    new ProductNotFoundException("입력한 상품이 존재하지 않습니다."));
                prices.add(new Price(productEntity.getPrice()));
            });

        return prices;
    }
}
