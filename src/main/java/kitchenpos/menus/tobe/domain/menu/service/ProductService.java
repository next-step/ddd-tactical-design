package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.domain.MenuProducts;

import java.util.List;

public interface ProductService {
    MenuProducts findAllPrices(List<Long> ids);
}
