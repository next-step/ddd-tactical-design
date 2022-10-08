package kitchenpos.products.application.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- 상품을 등록할 수 있다.
- 상품의 가격이 올바르지 않으면 등록할 수 없다.
  - 상품의 가격은 0원 이상이어야 한다.
- 상품의 이름이 올바르지 않으면 등록할 수 없다.
  - 상품의 이름에는 비속어가 포함될 수 없다.
- 상품의 가격을 변경할 수 있다.
- 상품의 가격이 올바르지 않으면 변경할 수 없다.
  - 상품의 가격은 0원 이상이어야 한다.
- 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
- 상품의 목록을 조회할 수 있다.
 */
class ProductTest {
    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void register() {
        final BigDecimal price = BigDecimal.TEN;
        final String name = "chicken";

        assertThatCode(() -> new Product(price, name))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품의 가격은 0보다 크거나 같아야 한다.")
    @Test
    void registerWithLessThanZero() {
        final BigDecimal price = BigDecimal.valueOf(-1L);
        final String name = "chicken";

        assertThatThrownBy(() -> new Product(price, name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
