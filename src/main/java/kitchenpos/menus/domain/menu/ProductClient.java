package kitchenpos.menus.domain.menu;

import java.util.List;

public interface ProductClient {

    void validateMenuProducts(final List<MenuProduct> menuProducts, final MenuPrice menuPrice);

    void validateMenuPrice(List<MenuProduct> menuProducts, MenuPrice menuPrice);
}
