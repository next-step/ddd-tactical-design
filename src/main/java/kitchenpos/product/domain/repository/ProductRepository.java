package kitchenpos.product.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByProductId(ProductId id);

    List<Product> findAll();

    List<Product> findAllByProductIdIn(List<ProductId> ids);
}

