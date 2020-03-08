package kitchenpos.products.dao;

import kitchenpos.products.model.ProductData;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    ProductData save(ProductData entity);

    Optional<ProductData> findById(Long id);

    List<ProductData> findAll();
}
