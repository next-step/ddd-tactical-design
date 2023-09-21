package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TobeProductClient {
    BigDecimal getProductPrice(UUID productId);
}
