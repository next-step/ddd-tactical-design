package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuCreateCommand(
    String name,
    BigDecimal price,
    UUID menuGroupId,
    boolean displayed,
    List<MenuProduct> menuProducts
) {
}
