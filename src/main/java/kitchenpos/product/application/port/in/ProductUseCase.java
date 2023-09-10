package kitchenpos.product.application.port.in;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

import java.math.BigDecimal;

public interface ProductUseCase {
    Product create(final Product request);

    Product changePrice(final ProductId productId, final BigDecimal price);
}