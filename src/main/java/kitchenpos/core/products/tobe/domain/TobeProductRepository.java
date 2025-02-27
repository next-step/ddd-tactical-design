package kitchenpos.core.products.tobe.domain;

import kitchenpos.core.shared.identifier.ProductId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TobeProductRepository {
    Product save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<ProductId> ids);
}

