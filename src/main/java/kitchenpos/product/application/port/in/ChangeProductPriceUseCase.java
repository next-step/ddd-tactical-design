package kitchenpos.product.application.port.in;

import kitchenpos.product.application.service.model.ChangeProductPriceRequest;
import kitchenpos.product.domain.model.Product;

import java.util.UUID;

public interface ChangeProductPriceUseCase {
    Product changePrice(final UUID productId, final ChangeProductPriceRequest request);
}
