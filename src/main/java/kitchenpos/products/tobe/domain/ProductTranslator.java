package kitchenpos.products.tobe.domain;

import java.util.UUID;

public interface ProductTranslator {
    void changeMenuStatus(final UUID productId);
}
