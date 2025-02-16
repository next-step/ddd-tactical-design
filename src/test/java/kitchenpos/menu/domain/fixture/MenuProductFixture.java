package kitchenpos.menu.domain.fixture;

import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.fixture.ProductFixture;

public record MenuProductFixture(Product 상품, long 수량) {

    private static final long DEFAULT_MENU_PRODUCT_QTY = 10;

    public static MenuProductFixture init() {
        return new MenuProductFixture(ProductFixture.init().create(), DEFAULT_MENU_PRODUCT_QTY);
    }

    public MenuProduct create() {
        var menuProduct = new MenuProduct();
        menuProduct.setProduct(상품);
        menuProduct.setQuantity(수량);

        return menuProduct;
    }
}

