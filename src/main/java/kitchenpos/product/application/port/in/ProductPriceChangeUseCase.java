package kitchenpos.product.application.port.in;

import java.util.UUID;
import kitchenpos.product.application.exception.NotExistProductException;
import kitchenpos.product.domain.ProductPrice;

public interface ProductPriceChangeUseCase {

    /**
     * @throws NotExistProductException id에 해당하는 product가 없을 때
     */
    void change(final UUID id, final ProductPrice price);
}
