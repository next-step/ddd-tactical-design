package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;

public record MenuVo() {

    public record MenuInfo(
        MenuId id,
        MenuName name,
        MenuPrice price,
        boolean displayed
    ) {
        public static MenuInfo fromEntity(Menu entity) {
            return new MenuInfo(entity.getMenuId(), entity.getName(), entity.getPrice(), entity.isDisplayed());
        }

        public UUID getMenuId() {
            return id.get();
        }

        public String getMenuName() {
            return name.get();
        }

        public BigDecimal getMenuPrice() {
            return price.get();
        }
    }

    public record Create(
        String name,
        MenuPrice price,
        MenuGroupId menuGroupId,
        boolean displayed,
        MenuProducts menuProducts
    ) {
    }

    public record Update(MenuId menuId, MenuPrice price) {}
}
