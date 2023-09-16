package kitchenpos.products.tobe.domain;

import java.util.UUID;

public interface ProductMenuService {
    void validateMenuPrice(final UUID productId);
}
