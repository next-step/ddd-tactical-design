package kitchenpos.order.common.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;

public record OrderRequest() {

    public record Create(
        @NotNull(message = "메뉴 이름은 필수입니다.")
        String name,

        @Positive(message = "메뉴 가격은 0보다 커야 합니다.")
        BigDecimal price,

        UUID menuGroupId,

        boolean displayed,

        @NotEmpty(message = "메뉴 구성 상품을 하나 이상 포함해야 합니다.")
        List<MenuProductCreate> menuProducts
    ) {

    }

    public record MenuProductCreate(UUID productId, long quantity) {}

    public record UpdatePrice(
        UUID menuId,

        @PositiveOrZero(message = "상품가격은 0원 이상이어야 합니다.")
        BigDecimal price
    ) {

        public MenuVo.Update toVo() {
            return new MenuVo.Update(MenuId.of(menuId), MenuPrice.of(price));
        }
    }
}
