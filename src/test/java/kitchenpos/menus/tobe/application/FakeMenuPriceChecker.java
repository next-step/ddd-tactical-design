package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuPriceChecker;
import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FakeMenuPriceChecker implements MenuPriceChecker {
    @Override
    public boolean isTotalPriceLowerThanMenu(BigDecimal menuPrice, List<MenuProduct> menuProducts) {
        return true;
    }

    @Override
    public void checkMenuPriceAndHideMenuIfTotalPriceLower(UUID productId) {
    }
}
