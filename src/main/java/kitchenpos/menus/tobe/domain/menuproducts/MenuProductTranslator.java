package kitchenpos.menus.tobe.domain.menuproducts;

import kitchenpos.menus.tobe.dto.MenuProductRequest;

import java.util.List;

public interface MenuProductTranslator {
    MenuProducts getMenuProducts(List<MenuProductRequest> menuProductRequests);
}
