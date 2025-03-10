package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductPriceService {

    BigDecimal getPriceByProductId(UUID productId);
}
