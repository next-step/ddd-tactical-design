package kitchenpos.support.product;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductAble {
    UUID getId();
    String getName();
    BigDecimal getPrice();
}
