package kitchenpos.menu.tobe.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;

public class CreateMenuCommand {

    public final String name;

    public final UUID menuGroupId;

    public final List<MenuProduct> menuProducts;

    public final boolean displayed;

    public final BigDecimal price;

    public CreateMenuCommand(
        final String name,
        final UUID menuGroupId,
        final List<MenuProduct> menuProducts,
        final boolean displayed,
        final BigDecimal price
    ) {
        this.name = name;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
        this.price = price;
    }
}
