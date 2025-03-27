package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuProductInfo(UUID id, BigDecimal price, MenuProductQuantity quantity) {
}
