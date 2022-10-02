package kitchenpos.products.tobe.domain;

import java.util.UUID;

public interface PricePolicy {

    void changedProductPrice(final UUID productId, final Long price);
}
