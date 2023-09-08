package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeProfanityClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("상품")
class ProductTest {

    private static final String NAME = "상품";

    @DisplayName("[성공] 상품을 생성한다")
    @Test
    void create() {
        assertThatNoException().isThrownBy(
                () -> Product.of(NAME, BigDecimal.TEN)
        );
    }

    @DisplayName("[성공] 상품의 가격을 바꾼다.")
    @Test
    void changePrice1() {
        //given
        Product product = Product.of(NAME, BigDecimal.TEN);
        //when
        product.changePrice(new ProductPrice(1000));
        //then
        assertThat(product.getPrice())
                .isEqualTo(new ProductPrice(1000));
    }
}
