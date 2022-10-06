package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class DummyPricePolicy implements PricePolicy {

    @Override
    public void changedProductPrice(final UUID productId, final Long price) {

    }
}
