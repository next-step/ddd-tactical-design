package kitchenpos.product.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductPriceTest {

    @DisplayName("이름이 같은 경우 동일하다.")
    @ParameterizedTest
    @ValueSource(longs = {10_000L})
    void success(Long input) {
        final var price1 = new ProductPrice(input);
        final var price2 = new ProductPrice(input);

        assertThat(price2).isEqualTo(price1);
    }

    @DisplayName("[실패] 싱픔의 가격은 필수로 입력해야한다.")
    @ParameterizedTest
    @NullSource
    void priceFailTest(BigDecimal input) {

        assertThrows(IllegalPriceException.class, () -> new ProductPrice(input));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1_000L, -10_000L, -1L})
    @DisplayName("[실패] 0원보다 적게 입력하는 경우 등록할 수 없다.")
    void priceFailTest2(final long input) {

        assertThrows(IllegalArgumentException.class, () -> new ProductPrice(input));
    }
}
