package kitchenpos.menu.tobe.domain;

import kitchenpos.fixture.tobe.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductTest {
    @Test
    @DisplayName("메뉴 상품은 상품, 수량, 싱픔과 수량에 대한 총 가격을 가진다.")
    void success() {
        final var product = ProductFixture.createProduct();
        final var menuProduct = new MenuProduct(product, 2);

        assertAll(
                "메뉴 상품 정보 group assertions",
                () -> assertThat(menuProduct.getProduct()).isEqualTo(product),
                () -> assertThat(menuProduct.getPrice()).isEqualTo(product.getProductPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
        );
    }
}
