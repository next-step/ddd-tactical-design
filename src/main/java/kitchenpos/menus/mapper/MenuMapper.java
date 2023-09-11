package kitchenpos.menus.mapper;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.dto.MenuDetailResponse;
import kitchenpos.menus.dto.MenuGroupDetailResponse;
import kitchenpos.menus.dto.MenuProductElement;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public final class MenuMapper {

    private MenuMapper() {
        throw new UnsupportedOperationException();
    }

    public static MenuGroupDetailResponse toMenuGroupDetailResponse(MenuGroup menuGroup) {
        return new MenuGroupDetailResponse(menuGroup.getId(), menuGroup.getName());
    }

    public static MenuProductElement toMenuProductElement(MenuProduct menuProduct) {
        if (isNull(menuProduct)) {
            return null;
        }

        return new MenuProductElement(menuProduct.getProductId(), menuProduct.getQuantity());
    }

    public static MenuDetailResponse toMenuDetailResponse(Menu menu) {
        return new MenuDetailResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                toMenuGroupDetailResponse(menu.getMenuGroup()),
                menu.isDisplayed(),
                menu.getMenuProducts().stream()
                        .map(MenuMapper::toMenuProductElement)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

}
