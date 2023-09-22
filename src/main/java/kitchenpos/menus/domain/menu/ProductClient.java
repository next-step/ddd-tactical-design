package kitchenpos.menus.domain.menu;

import java.util.List;
import java.util.UUID;

public interface ProductClient {

    ProductPrice getProductPrice(final UUID productId);

    void validateMenuProducts(final List<MenuProduct> menuProducts, final MenuPrice menuPrice);

    void validateMenuPrice(final List<MenuProduct> menuProducts, final MenuPrice menuPrice);
}
