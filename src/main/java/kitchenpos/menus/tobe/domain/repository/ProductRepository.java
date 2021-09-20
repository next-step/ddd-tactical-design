package kitchenpos.menus.tobe.domain.repository;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.model.Product;

public interface ProductRepository {
    List<Product> findAllByIdIn(List<UUID> productIds);
}
