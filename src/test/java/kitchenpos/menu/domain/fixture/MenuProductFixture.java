package kitchenpos.menu.domain.fixture;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.fixture.ProductFixture;
import kitchenpos.product.domain.model.ProductId;

public record MenuProductFixture(UUID 상품아이디, MenuProductQty 수량) {

    private static final MenuProductQty DEFAULT_MENU_PRODUCT_QTY = MenuProductQty.of(10);

    public static MenuProductFixture init() {
        return new MenuProductFixture(ProductFixture.init().toEntity().getProductId().get(), DEFAULT_MENU_PRODUCT_QTY);
    }

    public MenuProduct toEntity() {
        return new MenuProduct(ProductId.of(상품아이디), 수량);
    }
}

