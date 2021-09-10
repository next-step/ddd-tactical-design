package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.dto.MenuProductRequest;

import java.util.List;

public interface MenuProductTranslator {
    MenuProducts translateMenuProducts(List<MenuProductRequest> menuProductRequests);
}
