package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;

public record MenuVo() {

    public record MenuInfo(
        UUID id,
        MenuName name,
        MenuPrice price,
        boolean displayed
    ) {
        public static MenuInfo fromEntity(Menu entity) {
            return new MenuInfo(entity.getId(), entity.getName(), entity.getPrice(), entity.isDisplayed());
        }
    }

    public record Create(
        String name,
        MenuPrice price,
        UUID menuGroupId,
        boolean displayed,
        List<MenuProduct> menuProducts
    ) {
    }

    public record Update(UUID menuId, MenuPrice price) {}
}
