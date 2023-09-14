package kitchenpos.product.application.port.in;

import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductPrice;

public interface ProductRegistrationUseCase {

    /**
     * @throws IllegalArgumentException nameCandidate에 비속어가 포함되어 있을 때
     */
    void register(final Name productNameCandidate, final ProductPrice price);
}
