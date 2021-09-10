package kitchenpos.products.tobe.infra;

import java.util.UUID;

public interface ProductTranslator {
    void updateMenuStatus(final UUID productId);
}
