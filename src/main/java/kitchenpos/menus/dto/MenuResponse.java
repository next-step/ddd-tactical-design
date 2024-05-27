package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.Menu;

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
    public static MenuResponse of(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                MenuGroupResponse.of(menu.getMenuGroup()),
                menu.isDisplayed(),
                menu.getMenuProducts().stream()
                        .map(MenuProductResponse::of)
                        .toList()
        );
    }
}
