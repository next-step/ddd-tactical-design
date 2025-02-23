package kitchenpos.menu.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;

public record MenuRequest() {

    public record Create(
        @NotNull(message = "메뉴 이름은 필수입니다.")
        String name,

        @Positive(message = "메뉴 가격은 0보다 커야 합니다.")
        BigDecimal price,
        UUID menuGroupId,
        boolean displayed,

        @NotEmpty(message = "메뉴 구성 상품을 하나 이상 포함해야 합니다.")
        List<MenuProduct> menuProducts
    ) {

        public MenuVo.Create toVo() {
            return new MenuVo.Create(name, MenuPrice.of(price), menuGroupId, displayed, menuProducts);
        }
    }

    public record UpdatePrice(UUID menuId, BigDecimal price) {

        public MenuVo.Update toVo() {
            return new MenuVo.Update(menuId, MenuPrice.of(price));
        }
    }
}
