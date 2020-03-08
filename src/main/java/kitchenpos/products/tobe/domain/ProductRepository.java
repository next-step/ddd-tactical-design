package kitchenpos.products.tobe.domain;

import kitchenpos.products.model.ProductData;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    ProductData save(ProductData entity);

    Optional<ProductData> findById(Long id);

    List<ProductData> findAll();
}
