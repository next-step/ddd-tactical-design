package kitchenpos.product.application.port.in;

import kitchenpos.product.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface LoadProductListUseCase {
    List<Product> findAll(List<UUID> ids);
}
