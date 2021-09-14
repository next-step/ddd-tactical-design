package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.dto.MenuProductRequest;

import java.util.List;

public interface MenuProductTranslator {
    List<MenuProduct> getMenuProducts(List<MenuProductRequest> menuProductRequests);
}
