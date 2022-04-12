package kitchenpos.products.application.port.in;

import java.util.List;
import java.util.UUID;

import kitchenpos.products.domain.Product;

public interface ProductUseCase {
    Product create(Product request);

    Product changePrice(UUID productId, Product request);

    List<Product> findAll();
}
