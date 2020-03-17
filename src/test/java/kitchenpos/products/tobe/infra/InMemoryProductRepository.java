package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private Map<Long, Product> entities = new HashMap<>();

    @Override
    public Product save(Product product) {
        Product savedProduct = new Product.Builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .build();

        entities.put(savedProduct.getId(), savedProduct);

        return savedProduct;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<Product> list() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public boolean findByNameContaining(String name) {
        List<Product> products = this.list();

        for(Product product : products){
            if(name.equals(product.getName())){
                return true;
            }
        }

        return false;
    }
}
