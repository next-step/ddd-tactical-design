package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfo(UUID id, BigDecimal price) {
}
