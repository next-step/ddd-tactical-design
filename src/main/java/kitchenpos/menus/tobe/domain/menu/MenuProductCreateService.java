package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.shared.dto.MenuProductDto;

import java.util.List;

public interface MenuProductCreateService {
    List<MenuProduct> getMenuProducts(List<MenuProductDto> menuProductRequests);

    void valid(Menu menu);
}
