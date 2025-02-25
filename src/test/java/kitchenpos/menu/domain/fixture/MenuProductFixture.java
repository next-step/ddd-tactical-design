package kitchenpos.menu.domain.fixture;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.fixture.ProductFixture;

public record MenuProductFixture(UUID 상품아이디, MenuProductQty 수량) {

    private static final MenuProductQty DEFAULT_MENU_PRODUCT_QTY = MenuProductQty.of(10);

    public static MenuProductFixture init() {
        return new MenuProductFixture(ProductFixture.init().toEntity().getId(), DEFAULT_MENU_PRODUCT_QTY);
    }

    public MenuProduct toEntity() {
        return new MenuProduct(상품아이디, 수량);
    }
}

