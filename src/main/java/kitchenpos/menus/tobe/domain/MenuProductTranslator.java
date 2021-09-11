package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.dto.MenuProductRequest;

import java.util.List;

public interface MenuProductTranslator {
    MenuProducts translateMenuProducts(List<MenuProductRequest> menuProductRequests);
}
