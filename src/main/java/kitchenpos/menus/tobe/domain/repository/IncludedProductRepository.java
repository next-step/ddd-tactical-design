package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.entity.IncludedProduct;

import java.util.Optional;
import java.util.UUID;

public interface IncludedProductRepository {
    Optional<IncludedProduct> findById(UUID id);
}
