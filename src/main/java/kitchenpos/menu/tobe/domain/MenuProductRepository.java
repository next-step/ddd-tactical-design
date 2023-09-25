package kitchenpos.menu.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface MenuProductRepository {

    void bulkUpdateMenuProductPrice(UUID productId, BigDecimal bigDecimal);
}
