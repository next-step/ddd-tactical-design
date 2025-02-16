package kitchenpos.product.application.port.in;

import kitchenpos.product.domain.model.Product;

import java.util.List;

public interface LoadProductListUseCase {
    List<Product> findAll();
}
