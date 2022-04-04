package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeProductRepository {
    TobeProduct save(TobeProduct product);

    Optional<TobeProduct> findById(ProductId id);

    List<TobeProduct> findAll();

    List<TobeProduct> findAllByIdIn(List<ProductId> ids);
}

