package kitchenpos.menuproducts.domain;

import kitchenpos.menuproducts.dto.MenuProductRequest;

import java.util.List;

public interface MenuProductTranslator {
    MenuProducts getMenuProducts(List<MenuProductRequest> menuProductRequests);
}
