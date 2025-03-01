package kitchenpos.menu.domain.fixture;

import java.util.UUID;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.fixture.ProductFixture;
import kitchenpos.product.domain.model.ProductId;

public record MenuProductFixture(UUID 상품아이디, UUID 메뉴아이디, long 수량) {

    private static final long DEFAULT_MENU_PRODUCT_QTY = 10;

    public static MenuProductFixture init(UUID menuId) {
        return new MenuProductFixture(
            ProductFixture.init().toEntity().getProductId().get(),
            menuId,
            DEFAULT_MENU_PRODUCT_QTY
        );
    }

    public MenuProduct toEntity() {
        return new MenuProduct(ProductId.of(상품아이디), MenuId.of(메뉴아이디), MenuProductQty.of(수량));
    }

    public MenuRequest.MenuProductCreate create() {
        return new MenuRequest.MenuProductCreate(상품아이디, 수량);
    }
}

