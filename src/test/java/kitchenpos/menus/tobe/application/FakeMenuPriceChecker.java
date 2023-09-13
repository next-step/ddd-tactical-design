package kitchenpos.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.MenuPriceChecker;
import kitchenpos.apply.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FakeMenuPriceChecker implements MenuPriceChecker {
    @Override
    public boolean isTotalPriceLowerThanMenu(BigDecimal menuPrice, List<MenuProduct> menuProducts) {
        return false;
    }

    @Override
    public void checkMenuPriceAndHideMenuIfTotalPriceLower(UUID productId) {
    }
}
