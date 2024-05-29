package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductClient {
    BigDecimal productPrice(UUID productId);
}
