package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.infra.ProductDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductDao implements ProductDao {
    private final Map<String, Product> entities = new HashMap<>();

    @Override
    public Product save(Product product) {
        entities.put(product.getName(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(entities.values());
    }


}
