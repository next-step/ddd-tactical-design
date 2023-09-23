package kitchenpos.menus.application.mapper;

import kitchenpos.menus.application.dto.MenuProductResponse;
import kitchenpos.menus.application.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;

import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {
    public static MenuProductResponse toDto(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                menuProduct.getProductId(),
                menuProduct.getQuantityValue()
        );
    }

    public static List<MenuProductResponse> toDtos(MenuProducts menuProducts) {
        return menuProducts.getValues()
                .stream()
                .map(MenuMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MenuResponse toDto(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getNameValue(),
                menu.getPriceValue(),
                menu.getMenuGroupIdValue(),
                menu.isDisplayed(),
                MenuMapper.toDtos(menu.getMenuProducts())
        );
    }

    public static List<MenuResponse> toDtos(List<Menu> menus) {
        return menus.stream()
                .map(MenuMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
