package kitchenpos.apply.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MenuPriceChecker {
    boolean isTotalPriceLowerThanMenu(BigDecimal menuPrice, List<MenuProduct> menuProducts);

    void checkMenuPriceAndHideMenuIfTotalPriceLower(UUID productId);
}
