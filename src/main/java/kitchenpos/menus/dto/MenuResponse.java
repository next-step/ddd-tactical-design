package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuResponse(
        UUID id,
        String name,
        BigDecimal price,
        MenuGroupResponse menuGroup,
        boolean displayed,
        List<MenuProductResponse> menuProducts
) {
    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                MenuGroupResponse.from(menu.getMenuGroup()),
                menu.isDisplayed(),
                menu.getMenuProductList().stream()
                        .map(MenuProductResponse::from)
                        .toList()
        );
    }
}
