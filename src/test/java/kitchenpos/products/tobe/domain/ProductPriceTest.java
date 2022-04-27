package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NullAndNegativePriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.products.fixture.PriceFixture.만원;
import static kitchenpos.products.fixture.PriceFixture.이만원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ProductPriceTest {

    @Test
    @DisplayName("상품의 가격을 등록할 수 있다.")
    void test1() {
        assertDoesNotThrow(
            () -> new ProductPrice(만원)
        );
    }

    @ParameterizedTest
    @DisplayName("상품의 가격이 비어있거나 음수이면 NegativePriceException 예외 발생")
    @ValueSource(strings = "-1000")
    void test2(int price) {
        assertThatThrownBy(
            () -> new ProductPrice(price)
        ).isInstanceOf(NullAndNegativePriceException.class);
    }

    @Test
    @DisplayName("동등성 비교")
    void test3() {
        ProductPrice price = new ProductPrice(만원);
        assertAll(
            () -> assertThat(price).isEqualTo(new ProductPrice(만원)),
            () -> assertThat(price).isNotEqualTo(new ProductPrice(이만원))
        );
    }
}
