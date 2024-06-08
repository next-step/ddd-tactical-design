package kitchenpos.fixture;

import kitchenpos.menus.domain.tobe.menuproduct.MenuProduct;
import kitchenpos.menus.domain.tobe.menuproduct.MenuQuantity;

public class MenuProductFixture {

    public static MenuProduct createFired(long quantity) {
        return new MenuProduct(ProductFixture.createFired(), new MenuQuantity(quantity));
    }

    public static MenuProduct createSeasoned(long quantity) {
        return new MenuProduct(ProductFixture.createSeasoned(), new MenuQuantity(quantity));
    }
}
