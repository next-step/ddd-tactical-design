package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;

/*
- [x] 상품을 등록할 수 있다.
- [x] 상품의 가격을 변경할 수 있다.
- 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
- 상품의 목록을 조회할 수 있다.
 */
class ProductTest {
    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void register() {
        final ProductPrice price = new ProductPrice(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("chicken", new FakeProfanity());

        assertThatCode(() -> new Product(price, name))
                .doesNotThrowAnyException();
    }

    @DisplayName("가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductPrice price = new ProductPrice(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("chicken", new FakeProfanity());

        Product product = new Product(price, name);
        product.changePrice(new ProductPrice(BigDecimal.ONE));
    }
}
