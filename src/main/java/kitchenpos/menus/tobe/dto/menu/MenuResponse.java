package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProduct;

public record MenuResponse(UUID id,
                           String name,
                           BigDecimal price,
                           UUID menuGroupId,
                           boolean displayed,
                           List<MenuProduct> menuProducts) {

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
            menu.getId(),
            menu.getName(),
            menu.getPrice(),
            menu.getMenuGroupId(),
            menu.isDisplayed(),
            menu.getMenuProducts());
    }
}
