package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuVo;

public record MenuResponse() {
    public record GetMenu(
        UUID id,
        String name,
        BigDecimal price,
        boolean displayed,
        List<GetMenuProduct> menuProducts,
        UUID menuGroupId
    ) {
        public static GetMenu fromVo(MenuVo.MenuInfo vo) {
            return new GetMenu(
                            vo.getMenuId(),
                            vo.getMenuName(),
                            vo.getMenuPrice(),
                            vo.displayed(),
                            vo.menuProducts().get().stream()
                                .map(GetMenuProduct::fromVo)
                                .toList(),
                            vo.getMenuGroupId()
            );
        }
    }

    public record GetMenuProduct(
        UUID productId,
        long quantity
    ) {
        public static GetMenuProduct fromVo(MenuProduct vo) {
            return new GetMenuProduct(vo.getProductId().get(), vo.getQuantity().get());
        }
    }
}
