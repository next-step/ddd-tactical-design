package kitchenpos.menu.fixture;

import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.product.domain.Product;
import kitchenpos.product.fixture.ProductFixture;

public class MenuProductFixture {
    private static final long DEFAULT_QUANTITY = 1L;

    private MenuProductFixture() {
    }

    public static MenuProduct 기본_메뉴_상품() {
        return 메뉴_상품_생성(ProductFixture.기본_상품(), DEFAULT_QUANTITY);
    }

    public static MenuProduct 메뉴_상품_생성(Product product, long quantity) {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(1L);
        menuProduct.setProduct(product);
        menuProduct.setProductId(product.getId());
        menuProduct.setQuantity(quantity);

        return menuProduct;
    }
}