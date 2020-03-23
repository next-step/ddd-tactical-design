package kitchenpos.menus.tobe.menu.domain;

import java.util.List;

public interface Products {
    List<MenuProduct> getMenuProductsByProductIdsAndQuantities(final List<Long> productIds, final List<Long> quantities);
}
