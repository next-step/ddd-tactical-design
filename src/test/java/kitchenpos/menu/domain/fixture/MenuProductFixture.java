package kitchenpos.menu.domain.fixture;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.product.domain.fixture.ProductFixture;

public record MenuProductFixture(UUID 상품아이디, long 수량) {

    private static final long DEFAULT_MENU_PRODUCT_QTY = 10;

    public static MenuProductFixture init() {
        return new MenuProductFixture(ProductFixture.init().toEntity().getId(), DEFAULT_MENU_PRODUCT_QTY);
    }

    public MenuProduct toEntity() {
        return new MenuProduct(상품아이디, 수량);
    }
}

